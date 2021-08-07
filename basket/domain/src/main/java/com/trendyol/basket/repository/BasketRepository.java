package com.trendyol.basket.repository;

import com.trendyol.basket.entity.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketRepository {
    Optional<Basket> findByCustomerId(long customerId);
    void save(Basket basket);
    Optional<List<Basket>> findByProductId(long productId);
}
