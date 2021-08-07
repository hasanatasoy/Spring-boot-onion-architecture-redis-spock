package com.trendyol.basket.entity;

import java.math.BigDecimal;

public class BasketCampaign {
    private String displayName;
    private BigDecimal price;

    public BasketCampaign(String displayName, BigDecimal price) {
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
