package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author lei
 */
@Getter
public enum OrderStatusEnum {

    /**
     * 订单状态枚举
     */
    NEW(0, "新订单"),
    FINISHED(1, "已完成"),
    CANCEL(2, "已取消"),;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
