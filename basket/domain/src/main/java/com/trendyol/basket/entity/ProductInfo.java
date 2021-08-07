package com.trendyol.basket.entity;

import com.trendyol.basket.exception.ProductValidationException;
import java.math.BigDecimal;

public class ProductInfo {
    private long id;
    private String imageUrl;
    private String title;
    private int quantity;
    private BigDecimal price;
    private BigDecimal oldPrice;

    public ProductInfo(){}

    public ProductInfo(long id, String imageUrl, String title, int quantity, BigDecimal price, BigDecimal oldPrice){
        var isProductFieldsNotValid = false;
        isProductFieldsNotValid =
                id <= 0L
                || imageUrl == null || imageUrl.isEmpty()
                || title == null || title.isEmpty()
                || quantity <= 0
                || price == null || price.equals(BigDecimal.ZERO)
                || oldPrice == null;
        if(isProductFieldsNotValid)
            throw new ProductValidationException();
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.oldPrice = oldPrice;
    }

    public long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setQuantity(int quantity){
        if(quantity <= 0)
            throw new ProductValidationException();
        this.quantity = quantity;
    }
}
