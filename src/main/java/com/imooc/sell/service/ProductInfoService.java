package com.imooc.sell.service;

import com.imooc.sell.domain.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品信息接口
 * @author lei
 */
public interface ProductInfoService {
    /**
     * 查找所有上架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查找所有商品
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 通过id查找商品
     * @param id
     * @return
     */
    ProductInfo findById(String id);

    /**
     *  根据购物车减库存
     * @param list
     */
    void decreaseStock(List<CartDTO> list);

    /**
     *  根据购物车加库存
     * @param list
     */
    void increaseStock(List<CartDTO> list);
}
