package com.trendyol.basket.model.request;

import java.util.List;

public class UpdateBasketRequest {
    private long customerId;
    private long productId;
    private List<Long> campaignIds;
    private int quantity;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
