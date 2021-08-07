package com.trendyol.basket.services;

import com.trendyol.basket.entity.Basket;

import java.math.BigDecimal;
import java.util.List;

public interface BasketService {
    Basket add(long customerId, long productId, String imageUrl, String title, int quantity, BigDecimal price, BigDecimal oldPrice);
    Basket update(long customerId, long productId, int quantity);
    Basket get(long customerId);
    void addCampaignToBasket(long customerId, String campaignDisplayName, BigDecimal campaignPrice);
    void clearBasketCampaigns(long customerId);
    List<Basket> getByProductId(long productId);
}
