package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 商品状态枚举
 *
 * @author lei
 */
@Getter
public enum ProductStatusEnum {

    /**
     * 上下架枚举
     */
    UP (0,"在架"),
    DOWN (1,"下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
