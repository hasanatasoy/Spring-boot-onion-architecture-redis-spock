package com.trendyol.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){
        super("product.not.found");
    }
}
