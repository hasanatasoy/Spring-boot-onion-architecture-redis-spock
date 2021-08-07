package com.trendyol.basket.repository;

import com.trendyol.basket.entity.Basket;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BasketRepositoryImpl implements BasketRepository{
    @Override
    public Optional<Basket> findByCustomerId(long customerId) {
        return Optional.empty();
    }

    @Override
    public void save(Basket basket) {
    }

    @Override
    public Optional<List<Basket>> findByProductId(long productId) {
        return Optional.empty();
    }
}
