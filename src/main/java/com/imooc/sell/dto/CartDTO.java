package com.imooc.sell.dto;

import lombok.Data;

/**
 * 购物车
 * @author lei
 */
@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO() {
        super();
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
