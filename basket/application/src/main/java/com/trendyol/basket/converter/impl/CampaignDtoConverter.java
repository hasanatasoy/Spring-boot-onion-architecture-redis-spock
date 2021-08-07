package com.trendyol.basket.converter.impl;

import com.trendyol.basket.converter.Converter;
import com.trendyol.basket.entity.BasketCampaign;
import com.trendyol.basket.model.dto.CampaignDTO;
import org.springframework.stereotype.Component;

@Component
public class CampaignDtoConverter implements Converter<BasketCampaign, CampaignDTO> {
    @Override
    public CampaignDTO convert(BasketCampaign basketCampaign) {
        var campaignDTO = new CampaignDTO(basketCampaign.getDisplayName(), basketCampaign.getPrice());
        return campaignDTO;
    }
}
