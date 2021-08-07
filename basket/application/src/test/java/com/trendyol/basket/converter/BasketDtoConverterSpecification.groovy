package com.trendyol.basket.converter

import com.trendyol.basket.converter.impl.BasketDtoConverter
import com.trendyol.basket.converter.impl.BasketInfoDtoConverter
import com.trendyol.basket.converter.impl.CampaignDtoConverter
import com.trendyol.basket.converter.impl.ProductInfoDtoConverter
import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.ProductInfo
import spock.lang.Specification;

class BasketDtoConverterSpecification extends Specification {

    def "Test converter"(){
        setup:
            def productInfo = new ProductInfo(1L, "test", "test1", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(105))
            def productInfoConverter = new ProductInfoDtoConverter()
            def basket = new Basket(1L, productInfo)
            def campaignDtoConverter = new CampaignDtoConverter();
            def basketInfoConverter = new BasketInfoDtoConverter(campaignDtoConverter)
            def basketDtoConverter = new BasketDtoConverter(basketInfoConverter, productInfoConverter)
        when:
            def basketDTO = basketDtoConverter.convert(basket)
        then:
            basketDTO.getCustomerId() == basket.getCustomerId()
            basketDTO.getProducts().size() == basket.getProducts().size()
    }
}
