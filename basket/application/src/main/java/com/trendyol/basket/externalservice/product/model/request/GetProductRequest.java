package com.trendyol.basket.externalservice.product.model.request;

public class GetProductRequest {

    private long productId;

    public GetProductRequest() {}
    public GetProductRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
