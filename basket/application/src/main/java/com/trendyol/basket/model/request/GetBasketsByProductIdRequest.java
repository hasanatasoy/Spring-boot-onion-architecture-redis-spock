package com.trendyol.basket.model.request;

public class GetBasketsByProductIdRequest {
    private long productId;

    public GetBasketsByProductIdRequest() {
    }

    public GetBasketsByProductIdRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
