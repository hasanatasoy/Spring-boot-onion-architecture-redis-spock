package com.trendyol.basket.externalservice.customer.request;

public class GetCustomerRequest {
    private long customerId;

    public GetCustomerRequest() {}

    public GetCustomerRequest(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
