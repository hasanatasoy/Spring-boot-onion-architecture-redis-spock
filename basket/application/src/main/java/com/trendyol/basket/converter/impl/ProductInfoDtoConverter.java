package com.trendyol.basket.converter.impl;

import com.trendyol.basket.converter.Converter;
import com.trendyol.basket.entity.ProductInfo;
import com.trendyol.basket.model.dto.ProductInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductInfoDtoConverter implements Converter<ProductInfo, ProductInfoDTO> {

    @Override
    public ProductInfoDTO convert(ProductInfo productInfo) {
        var productInfoDTO = new ProductInfoDTO();
        productInfoDTO.setId(productInfo.getId());
        productInfoDTO.setPrice(productInfo.getPrice());
        productInfoDTO.setOldPrice(productInfo.getOldPrice());
        productInfoDTO.setQuantity(productInfo.getQuantity());
        productInfoDTO.setImageUrl(productInfo.getImageUrl());
        productInfoDTO.setTitle(productInfo.getTitle());
        return productInfoDTO;
    }
}
