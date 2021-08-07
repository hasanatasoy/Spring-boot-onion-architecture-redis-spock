package com.trendyol.campaign.model.response;

import com.trendyol.campaign.model.dto.CalculatedCampaign;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalculatedCampaignResponse {
    private long customerId;
    private List<CalculatedCampaign> calculatedCampaigns;
}
