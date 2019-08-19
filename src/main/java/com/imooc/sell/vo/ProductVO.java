package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品返回前端vo
 * @author lei
 */

@Data
public class ProductVO {


    @JsonProperty("name")//返回给前端，这样子再把对象序列化的时候返回给前端它就是一个name了。
    private String categoryName;


    @JsonProperty("type")//明确是什么名字，类目的名字那就是categoryName
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
