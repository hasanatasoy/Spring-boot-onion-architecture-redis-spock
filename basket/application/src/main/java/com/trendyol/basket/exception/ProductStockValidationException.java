package com.trendyol.basket.exception;

public class ProductStockValidationException extends RuntimeException{
    public ProductStockValidationException(){
        super("product.stock.not.found");
    }
}
