package com.trendyol.campaign.model.response;

import java.math.BigDecimal;

public class CalculatedCampaign {
    private String name;
    private BigDecimal toBeAppliedPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getToBeAppliedPrice() {
        return toBeAppliedPrice;
    }

    public void setToBeAppliedPrice(BigDecimal toBeAppliedPrice) {
        this.toBeAppliedPrice = toBeAppliedPrice;
    }
}
