package com.trendyol.campaign.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateAndCreateCampaignRequest {
    private long id;
    private String name;
    private BigDecimal toBeAppliedPrice;
    private BigDecimal threshold;
    private BigDecimal actualPrice;
}
