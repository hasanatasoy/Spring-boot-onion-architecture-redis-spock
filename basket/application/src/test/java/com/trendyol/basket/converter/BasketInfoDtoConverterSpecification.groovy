package com.trendyol.basket.converter

import com.trendyol.basket.converter.impl.BasketDtoConverter
import com.trendyol.basket.converter.impl.BasketInfoDtoConverter
import com.trendyol.basket.converter.impl.CampaignDtoConverter
import com.trendyol.basket.converter.impl.ProductInfoDtoConverter
import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.BasketInfo
import com.trendyol.basket.entity.ProductInfo;
import spock.lang.Specification;

class BasketInfoDtoConverterSpecification extends Specification {

    def "Test converter"(){
        setup:
            def productInfos = new ArrayList<ProductInfo>()
            def productInfo = new ProductInfo(1L, "test", "test", 5, BigDecimal.valueOf(100), BigDecimal.valueOf(200))
            productInfos.add(productInfo)
            def basketInfo = new BasketInfo(productInfos)
            def campaignDtoConverter = new CampaignDtoConverter()
            def basketInfoDtoConverter = new BasketInfoDtoConverter(campaignDtoConverter)
        when:
            def basketInfoDTO = basketInfoDtoConverter.convert(basketInfo)
        then:
            basketInfoDTO.getCampaignDTOs().size() == basketInfo.getCampaigns().size()
            basketInfoDTO.getGrandTotal() == basketInfo.getGrandTotal()
            basketInfoDTO.getSubTotal() == basketInfo.getSubTotal()
    }
}
