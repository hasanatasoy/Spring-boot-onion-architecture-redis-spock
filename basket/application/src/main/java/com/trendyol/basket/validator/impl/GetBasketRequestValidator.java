package com.trendyol.basket.validator.impl;

import com.trendyol.basket.exception.RequestValidationException;
import com.trendyol.basket.model.request.GetBasketRequest;
import com.trendyol.basket.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GetBasketRequestValidator implements Validator<GetBasketRequest> {
    @Override
    public void validate(GetBasketRequest getBasketRequest) {
        var validationErrors = new ArrayList<String>();
        if(getBasketRequest.getCustomerId() <= 0L){
            validationErrors.add("invalid.customer.id");
        }
        if(validationErrors.size() > 0)
            throw new RequestValidationException(validationErrors.stream().findFirst().get());
    }
}
