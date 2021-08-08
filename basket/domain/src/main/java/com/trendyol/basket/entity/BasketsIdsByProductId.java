package com.trendyol.basket.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;

@RedisHash("BasketsByProductId")
public class BasketsIdsByProductId {
    @Id
    private long productId;
    private HashSet<Long> basketIds;

    public BasketsIdsByProductId(){}

    public BasketsIdsByProductId(long productId, HashSet<Long> basketIds) {
        this.productId = productId;
        this.basketIds = basketIds;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public HashSet<Long> getBasketIds() {
        return basketIds;
    }

    public void setBasketIds(HashSet<Long> basketIds) {
        this.basketIds = basketIds;
    }
}
