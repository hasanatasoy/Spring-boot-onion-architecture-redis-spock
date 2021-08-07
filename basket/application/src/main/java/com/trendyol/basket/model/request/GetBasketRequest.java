package com.trendyol.basket.model.request;

public class GetBasketRequest {
    private long customerId;

    public GetBasketRequest(){}

    public GetBasketRequest(long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }
}
