package com.imooc.sell.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品对象
 * @author lei
 */
@Entity
@Data
@Table(name = "product_info")
public class ProductInfo {

    /**商品id*/
    @Id
    @Column(name = "product_id")
    private String productId;

    /**商品名字*/
    @Column(name = "product_name")
    private String productName;

    /**商品价格*/
    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**商品库存*/
    @Column(name = "product_stock")
    private Integer productStock;

    /**小图*/
    @Column(name = "product_icon")
    private String productIcon;

    /**类目编号*/
    @Column(name = "category_type")
    private Integer categoryType;

    /**商品描述*/
    @Column(name = "product_description")
    private String productDescription;

    /**商品状态 0正常 1下架*/
    @Column(name = "product_status")
    private Integer productStatus;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**更新时间*/
    @Column(name = "update_time")
    private Date updateTime;
}
