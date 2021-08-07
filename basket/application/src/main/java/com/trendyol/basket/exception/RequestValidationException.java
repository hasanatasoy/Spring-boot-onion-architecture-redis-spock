package com.trendyol.basket.exception;

import java.util.List;

public class RequestValidationException extends RuntimeException{

    public RequestValidationException(String message){
        super(message);
    }
}
