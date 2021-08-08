package com.trendyol.campaign.service.impl;

import com.trendyol.campaign.domain.Campaign;
import com.trendyol.campaign.domain.CampaignRepository;
import com.trendyol.campaign.exception.CampaignNotFoundException;
import com.trendyol.campaign.model.dto.CalculatedCampaign;
import com.trendyol.campaign.service.CampaignService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository){
        this.campaignRepository = campaignRepository;
    }

    @Override
    public CalculatedCampaign calculateCargoPrice(BigDecimal basketTotal) {
        //TODO: think about campaign selector
        var optCampaign = campaignRepository.findAll().stream().findFirst();
        if(optCampaign.isEmpty()){
            throw new CampaignNotFoundException();
        }
        var campaign = optCampaign.get();
        var calculatedCampaign = new CalculatedCampaign();
        calculatedCampaign.setName(campaign.getName());
        if(basketTotal.compareTo(campaign.getThreshold()) > 0){
            calculatedCampaign.setToBeAppliedPrice(campaign.getToBeAppliedPrice());
        }
        else{
            calculatedCampaign.setToBeAppliedPrice(campaign.getActualPrice());
        }
        return calculatedCampaign;
    }

    @Override
    public void updateCampaign(long id, String name, BigDecimal threshold, BigDecimal toBeAppliedPrice, BigDecimal actualPrice) {
        var optCampaign = campaignRepository.findById(id);
        if(optCampaign.isEmpty()){
            throw new CampaignNotFoundException();
        }
        var campaign = optCampaign.get();
        campaign.setName(name);
        campaign.setActualPrice(actualPrice);
        campaign.setThreshold(threshold);
        campaign.setToBeAppliedPrice(toBeAppliedPrice);
        campaignRepository.save(campaign);
    }

    @Override
    public void createCampaign(String name, BigDecimal threshold, BigDecimal toBeAppliedPrice, BigDecimal actualPrice) {
        var campaign = new Campaign();
        campaign.setName(name);
        campaign.setThreshold(threshold);
        campaign.setActualPrice(actualPrice);
        campaign.setToBeAppliedPrice(toBeAppliedPrice);
        campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> getAll() {
        return campaignRepository.findAll();
    }
}
