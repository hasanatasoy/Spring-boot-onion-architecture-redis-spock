package com.trendyol.basket.converter.impl;

import com.trendyol.basket.converter.Converter;
import com.trendyol.basket.entity.ProductInfo;
import com.trendyol.basket.externalservice.campaign.model.dto.CampaignProductInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class CampaignProductInfoDtoConverter implements Converter<ProductInfo, CampaignProductInfoDTO> {
    @Override
    public CampaignProductInfoDTO convert(ProductInfo productInfo) {
        return new CampaignProductInfoDTO(productInfo.getId(), productInfo.getQuantity(), productInfo.getPrice());
    }
}
