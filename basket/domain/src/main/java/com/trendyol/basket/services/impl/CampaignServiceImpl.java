package com.trendyol.basket.services.impl;

import com.trendyol.basket.entity.BasketCampaign;
import com.trendyol.basket.exception.CampaignNotFoundException;
import com.trendyol.basket.repository.CampaignRepository;
import com.trendyol.basket.services.CampaignService;
import org.springframework.stereotype.Service;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository){
        this.campaignRepository = campaignRepository;
    }

    @Override
    public BasketCampaign get(long id) {
        var campaign = campaignRepository.findById(id).orElseThrow(CampaignNotFoundException::new);
        return campaign;
    }
}
