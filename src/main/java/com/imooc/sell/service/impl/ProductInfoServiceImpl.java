package com.imooc.sell.service.impl;

import com.imooc.sell.domain.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 商品service实现类
 * @author lei
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Resource
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        // ProductInfo productInfo = new ProductInfo();
        // // 设置查询条件为上架
        // productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        // Example<ProductInfo> example = Example.of(productInfo);
        // return  productInfoRepository.findAll(example, pageable);
        return productInfoRepository.findProductInfoByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo findById(String id) {
        return productInfoRepository.findById(id).get();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void decreaseStock(List<CartDTO> list) {
        for (CartDTO cart : list) {
            ProductInfo productInfo = productInfoRepository.findById(cart.getProductId()).get();
            if (productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cart.getProductQuantity();
            if(result < 0) {
                throw new SellException(ResultEnum.STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void increaseStock(List<CartDTO> list) {
        for (CartDTO cart : list) {
            ProductInfo productInfo = productInfoRepository.findById(cart.getProductId()).get();
            if (productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cart.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
