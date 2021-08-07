package com.trendyol.campaign.model.request;

import java.math.BigDecimal;
import java.util.List;

public class CalculateCampaignRequest {
    private long customerId;
    private List<CampaignProduct> campaignProducts;
    private BigDecimal basketTotal;

    public BigDecimal getBasketTotal() {
        return basketTotal;
    }

    public void setBasketTotal(BigDecimal basketTotal) {
        this.basketTotal = basketTotal;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<CampaignProduct> getCampaignProducts() {
        return campaignProducts;
    }

    public void setCampaignProducts(List<CampaignProduct> campaignProducts) {
        this.campaignProducts = campaignProducts;
    }
}
