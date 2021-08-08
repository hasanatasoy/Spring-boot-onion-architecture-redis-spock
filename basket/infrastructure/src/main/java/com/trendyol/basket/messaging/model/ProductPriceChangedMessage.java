package com.trendyol.basket.messaging.model;

import java.math.BigDecimal;

public class ProductPriceChangedMessage {
    private long productId;
    private BigDecimal price;
    private BigDecimal oldPrice;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }
}
