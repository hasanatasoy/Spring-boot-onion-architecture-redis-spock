package com.trendyol.basket.model.dto;

import java.util.List;

public class BasketDTO {
    private long customerId;
    private List<ProductInfoDTO> products;
    private BasketInfoDTO basketInfoDTO;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<ProductInfoDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoDTO> products) {
        this.products = products;
    }

    public BasketInfoDTO getBasketInfoDTO() {
        return basketInfoDTO;
    }

    public void setBasketInfoDTO(BasketInfoDTO basketInfoDTO) {
        this.basketInfoDTO = basketInfoDTO;
    }
}
