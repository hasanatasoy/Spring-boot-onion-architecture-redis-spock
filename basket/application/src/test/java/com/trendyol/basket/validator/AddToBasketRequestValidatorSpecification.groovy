package com.trendyol.basket.validator

import com.trendyol.basket.exception.RequestValidationException
import com.trendyol.basket.model.request.AddToBasketRequest
import com.trendyol.basket.validator.impl.AddToBasketRequestValidator
import spock.lang.Specification

class AddToBasketRequestValidatorSpecification extends Specification{
    def "When add to basket request customer id less than zero then throw RequestValidationException"(){
        setup:
            def request = new AddToBasketRequest();
            request.setQuantity(5)
            request.setProductId(2L)
            request.setCustomerId(-2)
            def validator = new AddToBasketRequestValidator()
            when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When add to basket request customer id equal zero then throw RequestValidationException"(){
        setup:
            def request = new AddToBasketRequest();
            request.setQuantity(5)
            request.setProductId(2L)
            request.setCustomerId(0)
            def validator = new AddToBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When add to basket request product id less than zero then throw RequestValidationException"(){
        setup:
            def request = new AddToBasketRequest();
            request.setQuantity(5)
            request.setProductId(-7)
            request.setCustomerId(2L)
            def validator = new AddToBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When add to basket request product id equal zero then throw RequestValidationException"(){
        setup:
            def request = new AddToBasketRequest();
            request.setQuantity(5)
            request.setProductId(0)
            request.setCustomerId(1L)
            def validator = new AddToBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When add to basket request quantity less than zero then throw RequestValidationException"(){
        setup:
            def request = new AddToBasketRequest();
            request.setQuantity(-7)
            request.setProductId(3L)
            request.setCustomerId(2L)
            def validator = new AddToBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When add to basket request quantity equal zero then throw RequestValidationException"(){
        setup:
            def request = new AddToBasketRequest();
            request.setQuantity(0)
            request.setProductId(2L)
            request.setCustomerId(1L)
            def validator = new AddToBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }
}
