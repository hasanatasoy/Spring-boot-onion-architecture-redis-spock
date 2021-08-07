package com.trendyol.basket.externalservice.campaign.model.dto;

import java.math.BigDecimal;

public class CampaignProductInfoDTO {
    private long productId;
    private int quantity;
    private BigDecimal price;

    public CampaignProductInfoDTO(long productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
