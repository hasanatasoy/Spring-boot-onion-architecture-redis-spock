package com.trendyol.basket.exception;

public class GetProductException extends RuntimeException{
    public GetProductException(){
        super("get.product.error");
    }
}
