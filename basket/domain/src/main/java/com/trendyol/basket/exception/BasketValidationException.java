package com.trendyol.basket.exception;

public class BasketValidationException extends RuntimeException{
    public BasketValidationException(){
        super("basket.validation.error");
    }
}
