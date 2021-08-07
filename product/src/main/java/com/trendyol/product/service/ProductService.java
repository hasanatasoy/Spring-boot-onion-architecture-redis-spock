package com.trendyol.product.service;

import com.trendyol.product.domain.Product;

import java.math.BigDecimal;

public interface ProductService {
    void updatePrice(long productId, BigDecimal price);
    void updateStock(long productId, int quantity);
    void save(Product product);
    Product get(long productId);
    void sendPriceChangedMessage(long productId, BigDecimal price, BigDecimal oldPrice);
    void sendStockChangedMessage(long productId, int quantity, int oldQuantity);
}
