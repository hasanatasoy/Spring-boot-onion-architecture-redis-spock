package com.trendyol.basket.validator

import com.trendyol.basket.exception.RequestValidationException
import com.trendyol.basket.model.request.UpdateBasketRequest
import com.trendyol.basket.validator.impl.UpdateBasketRequestValidator
import spock.lang.Specification

class UpdateBasketRequestValidatorSpecification extends Specification{
    def "When update basket request customer id less than zero then throw RequestValidationException"(){
        setup:
            def request = new UpdateBasketRequest();
            request.setQuantity(5)
            request.setProductId(2L)
            request.setCustomerId(-2)
            def validator = new UpdateBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When update basket request customer id equal zero then throw RequestValidationException"(){
        setup:
            def request = new UpdateBasketRequest();
            request.setQuantity(5)
            request.setProductId(2L)
            request.setCustomerId(0)
            def validator = new UpdateBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When update basket request product id less than zero then throw RequestValidationException"(){
        setup:
            def request = new UpdateBasketRequest();
            request.setQuantity(5)
            request.setProductId(-7)
            request.setCustomerId(2L)
            def validator = new UpdateBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When update basket request product id equal zero then throw RequestValidationException"(){
        setup:
            def request = new UpdateBasketRequest();
            request.setQuantity(5)
            request.setProductId(0)
            request.setCustomerId(1L)
            def validator = new UpdateBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When update basket request quantity less than zero then throw RequestValidationException"(){
        setup:
            def request = new UpdateBasketRequest();
            request.setQuantity(-7)
            request.setProductId(3L)
            request.setCustomerId(2L)
            def validator = new UpdateBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When update basket request quantity equal zero then throw RequestValidationException"(){
        setup:
            def request = new UpdateBasketRequest();
            request.setQuantity(0)
            request.setProductId(2L)
            request.setCustomerId(1L)
            def validator = new UpdateBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }
}
