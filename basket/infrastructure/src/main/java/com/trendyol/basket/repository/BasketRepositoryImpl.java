package com.trendyol.basket.repository;

import com.trendyol.basket.entity.Basket;
import com.trendyol.basket.entity.BasketsIdsByProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BasketRepositoryImpl implements BasketRepository {

    private final BasketRedisRepository basketRedisRepository;
    private final BasketsIdsByProductIdRepository basketsIdsByProductIdRepository;

    public BasketRepositoryImpl(
            BasketRedisRepository basketRedisRepository,
            BasketsIdsByProductIdRepository basketsIdsByProductIdRepository) {
        this.basketRedisRepository = basketRedisRepository;
        this.basketsIdsByProductIdRepository = basketsIdsByProductIdRepository;
    }


    @Override
    public Optional<Basket> findByCustomerId(long customerId) {
        return basketRedisRepository.findById(customerId);
    }

    @Override
    public void save(Basket basket) {
        basketRedisRepository.save(basket);
    }

    @Override
    public Optional<BasketsIdsByProductId> findByProductId(long productId) {
        return basketsIdsByProductIdRepository.findById(productId);
    }

    @Override
    public void saveProductBaskets(BasketsIdsByProductId basketsIdsByProductId) {
        basketsIdsByProductIdRepository.save(basketsIdsByProductId);
    }
}
