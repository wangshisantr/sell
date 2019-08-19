package com.imooc.sell.repository;

import com.imooc.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author lei
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 通过商品状态查找商品信息
     * @param status
     * @return
     */
    List<ProductInfo> findProductInfoByProductStatus(Integer status);

}
