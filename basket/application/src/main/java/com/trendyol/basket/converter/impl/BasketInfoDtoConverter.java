package com.trendyol.basket.converter.impl;

import com.trendyol.basket.converter.Converter;
import com.trendyol.basket.entity.BasketInfo;
import com.trendyol.basket.model.dto.BasketInfoDTO;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class BasketInfoDtoConverter implements Converter<BasketInfo, BasketInfoDTO> {

    private final CampaignDtoConverter campaignDtoConverter;

    public BasketInfoDtoConverter(CampaignDtoConverter campaignDtoConverter){
        this.campaignDtoConverter = campaignDtoConverter;
    }

    @Override
    public BasketInfoDTO convert(BasketInfo basketInfo) {
        var basketInfoDTO = new BasketInfoDTO();
        var campaignDTOs = basketInfo.getCampaigns().stream()
                .map(campaignDtoConverter::convert)
                .collect(Collectors.toList());
        basketInfoDTO.setCampaignDTOs(campaignDTOs);
        basketInfoDTO.setSubTotal(basketInfo.getSubTotal());
        basketInfoDTO.setGrandTotal(basketInfo.getGrandTotal());
        return basketInfoDTO;
    }
}
