package com.trendyol.basket.converter

import com.trendyol.basket.converter.impl.BasketInfoDtoConverter
import com.trendyol.basket.converter.impl.CampaignDtoConverter
import com.trendyol.basket.converter.impl.CampaignProductInfoDtoConverter
import com.trendyol.basket.converter.impl.ProductInfoDtoConverter
import com.trendyol.basket.entity.BasketInfo
import com.trendyol.basket.entity.ProductInfo;
import spock.lang.Specification;

class CampaignProductInfoDtoConverterSpecification extends Specification {

    def "Test converter"(){
        setup:
            def productId = 1L
            def imageUrl = "test"
            def title = "test2"
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(200)
            def productInfo = new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
            def campaignProductInfoDtoConverter = new CampaignProductInfoDtoConverter()
        when:
            def campaignProductInfoDTO = campaignProductInfoDtoConverter.convert(productInfo)
        then:
            campaignProductInfoDTO.getPrice() == productInfo.getPrice()
            campaignProductInfoDTO.getQuantity() == productInfo.getQuantity()
            campaignProductInfoDTO.getProductId() == productInfo.getId()
    }
}
