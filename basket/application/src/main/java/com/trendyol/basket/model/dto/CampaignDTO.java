package com.trendyol.basket.model.dto;

import java.math.BigDecimal;

public class CampaignDTO {
    private String displayName;
    private BigDecimal price;

    public CampaignDTO(String displayName, BigDecimal price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
