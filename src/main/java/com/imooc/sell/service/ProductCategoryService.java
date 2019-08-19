package com.imooc.sell.service;

import com.imooc.sell.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
}
