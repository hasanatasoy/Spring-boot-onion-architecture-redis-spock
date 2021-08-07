package com.trendyol.basket.validator.impl;

import com.trendyol.basket.exception.RequestValidationException;
import com.trendyol.basket.model.request.UpdateBasketRequest;
import com.trendyol.basket.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UpdateBasketRequestValidator implements Validator<UpdateBasketRequest> {
    @Override
    public void validate(UpdateBasketRequest updateBasketRequest) {
        var validationErrors = new ArrayList<String>();
        if(updateBasketRequest.getCustomerId() <= 0L){
            validationErrors.add("invalid.customer.id");
        }
        if(updateBasketRequest.getProductId() <= 0L){
            validationErrors.add("invalid.product.id");
        }
        if(updateBasketRequest.getQuantity() <= 0){
            validationErrors.add("invalid.quantity");
        }
        if(validationErrors.size() > 0)
            throw new RequestValidationException(validationErrors.stream().findFirst().get());
    }
}
