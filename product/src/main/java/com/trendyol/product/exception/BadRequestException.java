package com.trendyol.product.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(){
        super("bad.request");
    }
}
