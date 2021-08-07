package com.trendyol.basket.exception;

public class BasketInfoValidationException extends RuntimeException{
    public BasketInfoValidationException(){
        super("basketInfo.validation.error");
    }
}
