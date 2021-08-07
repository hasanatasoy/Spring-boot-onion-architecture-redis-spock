package com.trendyol.basket

import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.ProductInfo
import com.trendyol.basket.exception.BasketValidationException
import com.trendyol.basket.exception.ProductValidationException
import spock.lang.Specification

class ProductInfoEntitySpecification extends Specification{

    def "When productId less than zero then throw ProductValidationException"(){
        setup:
            def productId = -2
            def imageUrl = "test.jpg"
            def title = "title test"
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When productId equal zero then throw ProductValidationException"(){
        setup:
            def productId = 0
            def imageUrl = "test.jpg"
            def title = "title test"
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When image url is null then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = null
            def title = "title test"
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When image url is empty then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = ""
            def title = "title test"
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When title is null then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = null
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When title is empty then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = ""
            def quantity = 5
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When quantity less than zero then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = -2
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When quantity equal zero then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 0
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When price is null then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 3
            def price = null
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When price is zero then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 3
            def price = BigDecimal.valueOf(0)
            def oldPrice = BigDecimal.valueOf(150)
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When old price is null then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 3
            def price = BigDecimal.valueOf(110)
            def oldPrice = null
        when:
            new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        then:
            thrown ProductValidationException
    }

    def "When setting zero quantity to product then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 3
            def price = BigDecimal.valueOf(110)
            def oldPrice = BigDecimal.valueOf(150)
            def productInfo = new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        when:
            productInfo.setQuantity(0)
        then:
            thrown ProductValidationException
    }

    def "When setting quantity less than zero to product then throw ProductValidationException"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 3
            def price = BigDecimal.valueOf(110)
            def oldPrice = BigDecimal.valueOf(150)
            def productInfo = new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        when:
            productInfo.setQuantity(-2)
        then:
            thrown ProductValidationException
    }

    def "When setting quantity to product then get quantity should return new quantity"(){
        setup:
            def productId = 5
            def imageUrl = "test1"
            def title = "test2"
            def quantity = 3
            def newQuantity = 5
            def price = BigDecimal.valueOf(110)
            def oldPrice = BigDecimal.valueOf(150)
            def productInfo = new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice)
        when:
            productInfo.setQuantity(newQuantity)
        then:
            productInfo.getQuantity() == newQuantity
    }
}
