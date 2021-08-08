package com.trendyol.basket.repository;

import com.trendyol.basket.entity.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRedisRepository extends CrudRepository<Basket, Long> {
}
