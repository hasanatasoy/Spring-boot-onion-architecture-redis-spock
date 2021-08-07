package com.trendyol.basket.externalservice.campaign.model.response;

import com.trendyol.basket.model.dto.CampaignDTO;

import java.util.List;

public class GetCampaignResponse {
    private long customerId;
    private List<CampaignDTO> campaignDTOs;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<CampaignDTO> getCampaignDTOs() {
        return campaignDTOs;
    }

    public void setCampaignDTOs(List<CampaignDTO> campaignDTOs) {
        this.campaignDTOs = campaignDTOs;
    }
}
