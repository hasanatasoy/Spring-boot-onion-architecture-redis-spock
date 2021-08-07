package com.trendyol.campaign.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CalculateCampaignRequest {
    private long customerId;
    private List<CampaignProduct> campaignProducts;
    private BigDecimal basketTotal;
}
