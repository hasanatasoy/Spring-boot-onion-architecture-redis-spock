package com.trendyol.basket.exception;

public class ProductValidationException extends RuntimeException{

    public ProductValidationException(){
        super("product.validation.error");
    }
}
