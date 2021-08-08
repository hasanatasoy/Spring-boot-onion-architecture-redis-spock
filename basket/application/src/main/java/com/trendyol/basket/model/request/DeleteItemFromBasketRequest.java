package com.trendyol.basket.model.request;

public class DeleteItemFromBasketRequest {

    private long customerId;
    private long productId;

    public DeleteItemFromBasketRequest(){}

    public DeleteItemFromBasketRequest(long productId) {
        this.productId = productId;
    }

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
}
