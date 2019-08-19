package com.imooc.sell.service.impl;

import com.imooc.sell.domain.OrderDetail;
import com.imooc.sell.domain.OrderMaster;
import com.imooc.sell.domain.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单service
 *
 * @author lei
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final  ProductInfoService productInfoService;
    private final  OrderDetailRepository orderDetailRepository;
    private final  OrderMasterRepository orderMasterRepository;

    @Autowired
    public OrderServiceImpl(ProductInfoService productInfoService,OrderDetailRepository orderDetailRepository,
                            OrderMasterRepository orderMasterRepository) {
        this.productInfoService = productInfoService;
        this.orderDetailRepository = orderDetailRepository;
        this.orderMasterRepository = orderMasterRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    /**
     * 创建订单参数:
     * name: "张三"
     * phone: "18868822111"
     * address: "慕课网总部"
     * openid: "ew3euwhd7sjw9diwkq" //用户的微信openid
     * items: [{
     *     productId: "1423113435324",
     *     productQuantity: 2 //购买数量
     * }]
     */
    public OrderDTO create(OrderDTO orderDTO) {
        log.info("创建订单参数orderDTO：" + orderDTO.toString());
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = CodeUtil.uniqueCode();
        Date nowDate = new Date();
        // 查询商品详情（单价）
        for(OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算总价
           orderAmount = productInfo.getProductPrice().multiply(
                    new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            // 订单详情入库
            // copy productName productPrice productQuantity productIcon time
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(CodeUtil.uniqueCode());
            orderDetail.setOrderId(orderId);
            orderDetail.setCreateTime(nowDate);
            orderDetailRepository.save(orderDetail);
        }
        // 订单入库（orderMaster）
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setCreateTime(nowDate);
        orderMasterRepository.save(orderMaster);
        // 减库存
        List<CartDTO> cartList = orderDTO.getOrderDetails().stream().map(
                e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartList);
        orderDTO.setOrderId(orderId);
        log.info("订单创建成功，返回：" + orderDTO.toString());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetails(orderDetails);
        log.info("获取单个订单信息:" + orderDTO.toString());
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String openid, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(openid, pageable);
        List<OrderDTO> orderList = orderMasters.stream().map(e -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e, orderDTO);
            return orderDTO;
        }).collect(Collectors.toList());
        Page<OrderDTO> page = new PageImpl<>(orderList, pageable, orderMasters.getTotalElements());
        return page;
    }

    @Override
    public OrderDTO finished(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模版消息
        // pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderDTO cancel(String orderId) {
        // 判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 更新订单状态
        orderMaster.setPayStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterRepository.save(orderMaster);
        // 增加库存
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if(orderDetails == null ) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartList = orderDetails.stream().map(
                e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartList);
        // 如果已支付，退款
        if(orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
