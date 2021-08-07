package com.trendyol.basket.converter

import com.trendyol.basket.converter.impl.BasketInfoDtoConverter
import com.trendyol.basket.converter.impl.CampaignDtoConverter
import com.trendyol.basket.entity.BasketCampaign
import spock.lang.Specification;

class CampaignDtoConverterSpecification extends Specification {

    def "Test converter"(){
        setup:
            def campaignDisplayName = "Campaign1"
            def campaignPrice = BigDecimal.valueOf(120)
            def basketCampaign = new BasketCampaign(campaignDisplayName, campaignPrice)
            def campaignDtoConverter = new CampaignDtoConverter()
        when:
            def campaignDTO = campaignDtoConverter.convert(basketCampaign)
        then:
            campaignDTO.getPrice() == basketCampaign.getPrice()
            campaignDTO.getDisplayName() == basketCampaign.getDisplayName()
    }
}
