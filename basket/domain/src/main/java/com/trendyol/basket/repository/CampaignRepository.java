package com.trendyol.basket.repository;

import com.trendyol.basket.entity.BasketCampaign;

import java.util.Optional;

public interface CampaignRepository {
    Optional<BasketCampaign> findById(long id);
}
