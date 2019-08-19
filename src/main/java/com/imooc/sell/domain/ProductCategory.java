package com.imooc.sell.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 * @author lei
 */
@Data
@Entity
@DynamicUpdate
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    /**类目名字*/
    private String categoryName;

    /**类目编号*/
    private Integer categoryType;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;
}
