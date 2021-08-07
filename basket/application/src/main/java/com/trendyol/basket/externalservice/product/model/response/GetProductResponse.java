package com.trendyol.basket.externalservice.product.model.response;

import com.trendyol.basket.model.dto.ProductInfoDTO;

public class GetProductResponse {
    private ProductInfoDTO productInfoDto;

    public GetProductResponse(){}

    public GetProductResponse(ProductInfoDTO productInfoDto) {
        this.productInfoDto = productInfoDto;
    }

    public ProductInfoDTO getProductInfoDto() {
        return productInfoDto;
    }

    public void setProductInfoDto(ProductInfoDTO productInfoDto) {
        this.productInfoDto = productInfoDto;
    }
}
