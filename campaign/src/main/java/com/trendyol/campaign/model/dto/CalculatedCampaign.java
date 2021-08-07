package com.trendyol.campaign.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CalculatedCampaign {
    private String name;
    private BigDecimal toBeAppliedPrice;
}
