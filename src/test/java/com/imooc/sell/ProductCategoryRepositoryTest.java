package com.imooc.sell;


import com.imooc.sell.domain.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    public void findAll() {
        List<ProductCategory> list = productCategoryRepository.findAll();
        System.err.println(list.toString());
    }

    @Test
    public void findOne() {
        // 用getOne()会报一个no session的异常，可在实体类上加上注解@Proxy(lazy=false)
        ProductCategory productCategory = productCategoryRepository.findById(1).get();
        System.err.println(productCategory.toString());
    }

    // @Test
    // public void insert() {
    //     ProductCategory p = new ProductCategory();
    //     p.setCategoryName("衣服");
    //     p.setCategoryType(new Random().nextInt(100000) + "");
    //     p.setCreateTime(new Date());
    //     productCategoryRepository.save(p);
    // }

    @Test
    public void update() {
        ProductCategory p = new ProductCategory();
        p.setCategoryName("衣服");
        p.setCategoryType(111);
        p.setCreateTime(new Date());
        p.setCategoryId(1);
        productCategoryRepository.save(p);
        // productCategoryRepository.deleteById(1);
    }
}