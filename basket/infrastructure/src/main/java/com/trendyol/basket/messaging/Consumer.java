package com.trendyol.basket.messaging;

public interface Consumer<T>{
    void consume(T t);
}
