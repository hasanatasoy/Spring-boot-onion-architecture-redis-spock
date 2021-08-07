package com.trendyol.basket.validator.impl;

import com.trendyol.basket.exception.RequestValidationException;
import com.trendyol.basket.model.request.AddToBasketRequest;
import com.trendyol.basket.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddToBasketRequestValidator implements Validator<AddToBasketRequest> {
    @Override
    public void validate(AddToBasketRequest addToBasketRequest) {
        var validationErrors = new ArrayList<String>();
        if(addToBasketRequest.getCustomerId() <= 0L){
            validationErrors.add("invalid.customer.id");
        }
        if(addToBasketRequest.getProductId() <= 0L){
            validationErrors.add("invalid.product.id");
        }
        if(addToBasketRequest.getQuantity() <= 0L){
            validationErrors.add("invalid.quantity");
        }
        if(validationErrors.size() > 0)
            throw new RequestValidationException(validationErrors.stream().findFirst().get());
    }
}
