package com.imooc.sell.controller;

import com.imooc.sell.domain.ProductCategory;
import com.imooc.sell.domain.ProductInfo;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.SellResultVO;
import com.imooc.sell.vo.ProductInfoVO;
import com.imooc.sell.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息controller
 *
 * @author lei
 */
@RestController
@RequestMapping("/buyer/product")
@Api(tags = "商品数据接口")
@Slf4j
public class ProductController {
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    ProductCategoryService productCategoryService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/list")
    @ApiOperation(value = "获取数据列表")
    public SellResultVO getList() {
        Page<ProductInfo> infoPage = productInfoService.findAll(PageRequest.of(0, 10));
        return new SellResultVO(infoPage);
    }

    @GetMapping("/list/up")
    @ApiOperation(value = "获取上架数据列表")
    public SellResultVO getUpList() {
        // 查询所有上架商品
        List<ProductInfo> productList = productInfoService.findUpAll();
        // 抽取类目编号
        List<Integer> typeList = productList.stream().map(
                e -> e.getCategoryType()).collect(Collectors.toList());
        // 查询类目
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(typeList);
        // 数据拼装
        List<ProductVO> productVoList = new ArrayList<>();
        // 遍历类目列表
        for (ProductCategory category : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());
            List<ProductInfoVO> productInfoVoList = new ArrayList<>();
            // 遍历商品列表
            for (ProductInfo product : productList) {
                // 只有类目编号type相等的时候才放入数据
                if (product.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(product, productInfoVO);
                    productInfoVoList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVoList);
            productVoList.add(productVO);
        }
        return new SellResultVO(productVoList);
    }
}
