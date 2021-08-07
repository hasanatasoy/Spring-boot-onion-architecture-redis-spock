package com.trendyol.basket.model.dto;

import com.trendyol.basket.entity.ProductInfo;

import java.math.BigDecimal;

public class ProductInfoDTO {
    private long id;
    private String imageUrl;
    private String title;
    private int quantity;
    private BigDecimal price;
    private BigDecimal oldPrice;

    public ProductInfoDTO(){}

    public ProductInfoDTO(long id, String imageUrl, String title, int quantity, BigDecimal price, BigDecimal oldPrice) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.oldPrice = oldPrice;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
}
