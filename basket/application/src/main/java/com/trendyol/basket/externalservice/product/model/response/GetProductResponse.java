package com.trendyol.basket.externalservice.product.model.response;

import com.trendyol.basket.model.dto.ProductInfoDTO;

public class GetProductResponse {
    private ProductInfoDTO productInfoDto;

    public ProductInfoDTO getProductDTO() {
        return productInfoDto;
    }

    public void setProductDTO(ProductInfoDTO productInfoDto) {
        this.productInfoDto = productInfoDto;
    }
}
