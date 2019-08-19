package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单service接口
 * @author lei
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderDTO
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询订单列表
     * @param openid
     * @param pageable
     * @return
     */
    Page<OrderDTO> findList(String openid, Pageable pageable);

    /**
     * 完结订单
     * @param  orderDTO
     * @return
     */
    OrderDTO finished (OrderDTO orderDTO);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    OrderDTO cancel (String orderId);

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    OrderDTO paid(OrderDTO orderDTO);
}
