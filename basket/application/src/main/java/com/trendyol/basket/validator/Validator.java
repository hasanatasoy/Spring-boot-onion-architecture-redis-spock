package com.trendyol.basket.validator;

public interface Validator<Request> {
    void validate(Request request);
}
