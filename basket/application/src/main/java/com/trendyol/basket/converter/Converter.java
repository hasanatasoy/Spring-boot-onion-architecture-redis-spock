package com.trendyol.basket.converter;

public interface Converter<T,R> {
    R convert(T t);
}
