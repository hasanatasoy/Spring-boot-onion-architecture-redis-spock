package com.trendyol.basket.externalservice.notification.request;

import java.math.BigDecimal;

public class NotificationProductInfoRequest {
    private int quantity;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private String title;

    public NotificationProductInfoRequest(){}

    public NotificationProductInfoRequest(int quantity, BigDecimal price, BigDecimal oldPrice, String title) {
        this.quantity = quantity;
        this.price = price;
        this.oldPrice = oldPrice;
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
