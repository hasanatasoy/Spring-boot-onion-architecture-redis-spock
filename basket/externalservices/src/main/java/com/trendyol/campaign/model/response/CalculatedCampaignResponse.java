package com.trendyol.campaign.model.response;

import java.util.List;

public class CalculatedCampaignResponse {
    private List<CalculatedCampaign> calculatedCampaigns;

    public List<CalculatedCampaign> getCalculatedCampaigns() {
        return calculatedCampaigns;
    }

    public void setCalculatedCampaigns(List<CalculatedCampaign> calculatedCampaigns) {
        this.calculatedCampaigns = calculatedCampaigns;
    }
}
