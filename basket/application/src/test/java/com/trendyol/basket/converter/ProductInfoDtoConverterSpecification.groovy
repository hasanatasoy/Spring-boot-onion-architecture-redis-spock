package com.trendyol.basket.converter

import com.trendyol.basket.converter.impl.ProductInfoDtoConverter
import com.trendyol.basket.entity.ProductInfo;
import spock.lang.Specification;

class ProductInfoDtoConverterSpecification extends Specification {

    def "Test converter"(){
        setup:
        def productId = 1L
        def imageUrl = "test"
        def title = "test2"
        def quantity = 5
        def price = BigDecimal.valueOf(100)
        def oldPrice = BigDecimal.valueOf(200)
        def productInfo = new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        def productInfoDtoConverter = new ProductInfoDtoConverter()
        when:
        def productInfoDTO = productInfoDtoConverter.convert(productInfo)
        then:
        productInfoDTO.getPrice() == productInfo.getPrice()
        productInfoDTO.getOldPrice() == productInfo.getOldPrice()
        productInfoDTO.getTitle() == productInfo.getTitle()
        productInfoDTO.getQuantity() == productInfo.getQuantity()
        productInfoDTO.getImageUrl() == productInfo.getImageUrl()
        productInfoDTO.getId() == productInfo.getId()
    }
}
