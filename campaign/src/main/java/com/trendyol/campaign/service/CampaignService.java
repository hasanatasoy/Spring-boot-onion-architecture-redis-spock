package com.trendyol.campaign.service;

import com.trendyol.campaign.domain.Campaign;
import com.trendyol.campaign.model.dto.CalculatedCampaign;

import java.math.BigDecimal;
import java.util.List;

public interface CampaignService {
    CalculatedCampaign calculateCargoPrice(BigDecimal basketTotal);
    void updateCampaign(long id, String name, BigDecimal threshold, BigDecimal toBeAppliedPrice, BigDecimal actualPrice);
    void createCampaign(String name, BigDecimal threshold, BigDecimal toBeAppliedPrice, BigDecimal actualPrice);
    List<Campaign> getAll();
}
