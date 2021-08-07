package com.trendyol.basket.validator

import com.trendyol.basket.exception.RequestValidationException
import com.trendyol.basket.model.request.GetBasketRequest
import com.trendyol.basket.validator.impl.GetBasketRequestValidator
import spock.lang.Specification

class GetBasketRequestValidatorSpecification extends Specification{
    def "When get basket request customer id less than zero then throw RequestValidationException"(){
        setup:
            def request = new GetBasketRequest()
            request.setCustomerId(-71)
            def validator = new GetBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }

    def "When get basket request customer id equal zero then throw RequestValidationException"(){
        setup:
            def request = new GetBasketRequest()
            request.setCustomerId(0)
            def validator = new GetBasketRequestValidator()
        when:
            validator.validate(request)
        then:
            thrown RequestValidationException
    }
}
