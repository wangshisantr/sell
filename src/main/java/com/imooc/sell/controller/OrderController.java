package com.imooc.sell.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.domain.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.util.SellResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单controller
 *
 * @author lei
 */
@RestController
@RequestMapping("/sell/buyer/order")
@Slf4j
@Api(tags = "买家订单接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    @ApiOperation("创建订单")
    public SellResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        log.info("创建订单入参，orderForm={}", orderForm);
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetails = new Gson().fromJson(orderForm.getItems(),
                new TypeToken<List<OrderDetail>>() {
                }.getType());
        orderDTO.setOrderDetails(orderDetails);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO dto;
        try {
            dto = orderService.create(orderDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return SellResultVO.fail();
        }
        Map<String, String> map = new HashMap<>(1);
        map.put("orderId", dto.getOrderId());
        return SellResultVO.success(map);
    }

    @GetMapping("/detail")
    @ApiOperation("查看订单详情")
    public SellResultVO getDetail(String orderId, String openid) {
        log.info("查询订单详情参数{}", "openid:" + openid + ",orderId:" + orderId);
        OrderDTO dto = this.checkOrderOwner(openid, orderId);
        return SellResultVO.success(dto);
    }

    @GetMapping("/list")
    @ApiOperation("查看订单列表")
    public SellResultVO getList(String openid, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> dtoPage = orderService.findList(openid, pageRequest);
        return SellResultVO.success(dtoPage.getContent());
    }

    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    public SellResultVO cancel(String orderId, String openid) {
        checkOrderOwner(openid, orderId);
        return SellResultVO.success();
    }

    /**
     * 检查订单的openid与传入的openid是否一致
     *
     * @param openid
     * @param orderId
     * @return
     */
    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (!openid.equals(orderDTO.getBuyerOpenid())) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
