package com.trendyol.basket.externalservice.product;

import com.trendyol.basket.externalservice.product.model.request.GetProductRequest;
import com.trendyol.basket.externalservice.product.model.response.GetProductResponse;

public interface ProductService {
    GetProductResponse get(GetProductRequest getProductRequest);
}
