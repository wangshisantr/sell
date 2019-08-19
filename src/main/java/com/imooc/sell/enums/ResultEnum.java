package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 自定义异常返回枚举
 *
 * @author lei
 */
@Getter
public enum ResultEnum {
    /**
     * 自定义异常枚举
     */
    PARAM_ERROR(1, "参数不正确"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    STOCK_ERROR(20, "库存错误"),
    ORDER_NOT_EXIST(30, "订单不存在"),
    ORDER_MASTER_NOT_EXIST(40, "订单详情不存在"),
    ORDER_STATUS_ERROR(50, "订单状态不正确"),
    ORDER_DETAIL_EMPTY(60, "订单详情为空"),
    ORDER_UPDATE_FAIL(70, "订单更新失败"),
    ORDER_PAY_STATUS_ERROR(80, "订单支付状态不正确"),
    ORDER_OWNER_ERROR(85, "订单不属于该用户"),
    CART_EMPTY(90, "购物车不能为空"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
