package com.trendyol.basket.exception;

public class BasketNotFoundException extends RuntimeException{

    public BasketNotFoundException(){
        super("not.found.basket");
    }
}
