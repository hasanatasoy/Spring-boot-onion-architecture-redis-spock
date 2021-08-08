package com.trendyol.basket.repository;

import com.trendyol.basket.entity.Basket;
import com.trendyol.basket.entity.BasketsIdsByProductId;

import java.util.Optional;

public interface BasketRepository {
    Optional<Basket> findByCustomerId(long customerId);
    void save(Basket basket);
    Optional<BasketsIdsByProductId> findByProductId(long productId);
    void saveProductBaskets(BasketsIdsByProductId basketsIdsByProductId);
}
