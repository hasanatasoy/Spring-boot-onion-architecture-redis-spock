package com.trendyol.basket

import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.ProductInfo
import com.trendyol.basket.exception.BasketValidationException
import spock.lang.Specification

class BasketEntitySpecification extends Specification{

    def "When customerId equal 0 while creating basket then throw BasketValidationException"(){
        setup:
            def customerId = 0
            def productInfo = new ProductInfo(1L, "test", "test1", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(105))
        when:
            new Basket(customerId, productInfo)
        then:
            thrown BasketValidationException
    }

    def "When customerId less than 0 while creating basket then throw BasketValidationException"(){
        setup:
            def customerId = -5
            def productInfo = new ProductInfo(1L, "test", "test1", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(105))
        when:
            new Basket(customerId, productInfo)
        then:
            thrown BasketValidationException
    }

    def "When productInfo is null then throw BasketValidationException"(){
        setup:
            def customerId = 0
            def productInfo = null
        when:
            new Basket(customerId, productInfo)
        then:
            thrown BasketValidationException
    }

    def "Product list should be initialized"(){
        setup:
            def customerId = 1L
            def productInfo = new ProductInfo(1L, "test", "test1", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(105))
        when:
            def basket = new Basket(customerId, productInfo)
        then:
            basket.getProducts() != null
    }

    def "ProductInfo should be added to product list in basket"(){
        setup:
            def customerId = 1L
            def productInfo = new ProductInfo(1L, "test", "test1", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(105))
        when:
            def basket = new Basket(customerId, productInfo)
        then:
            basket.getProducts().first().getId() == 1L
            basket.getProducts().first().getImageUrl() == "test"
            basket.getProducts().first().getPrice() == BigDecimal.valueOf(100)
            basket.getProducts().first().getOldPrice() == BigDecimal.valueOf(105)
            basket.getProducts().first().title == "test1"
            basket.getProducts().first().getQuantity() == 2
    }

    def "BasketInfo should be initialized"(){
        setup:
            def customerId = 1L
            def productInfo = new ProductInfo(1L, "test", "test1", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(105))
        when:
            def basket = new Basket(customerId, productInfo)
        then:
            basket.getBasketInfo() != null
    }
}
