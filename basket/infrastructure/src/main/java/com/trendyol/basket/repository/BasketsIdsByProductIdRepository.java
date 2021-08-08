package com.trendyol.basket.repository;

import com.trendyol.basket.entity.BasketsIdsByProductId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketsIdsByProductIdRepository extends CrudRepository<BasketsIdsByProductId, Long> {
}
