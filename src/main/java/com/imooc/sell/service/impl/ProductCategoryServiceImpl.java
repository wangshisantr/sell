package com.imooc.sell.service.impl;

import com.imooc.sell.domain.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import com.imooc.sell.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类目service实现类
 * @author lei
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Resource
    ProductCategoryRepository repository;
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> list) {
        return repository.findByCategoryTypeIn(list);
    }
}
