package com.trendyol.basket.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("product.not.found");
    }
}
