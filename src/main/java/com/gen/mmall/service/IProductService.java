package com.gen.mmall.service;

import com.gen.mmall.common.ServerResponse;
import com.gen.mmall.pojo.Product;
import com.gen.mmall.vo.ProductDetailVo;

public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
}
