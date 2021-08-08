package com.trendyol.basket.exception;

public class BasketIdsByProductIdNotFoundException extends RuntimeException{
    public BasketIdsByProductIdNotFoundException(){
        super("basket.id.not.found.in.basketids.by.productid");
    }
}
