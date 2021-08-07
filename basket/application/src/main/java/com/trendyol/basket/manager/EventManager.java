package com.trendyol.basket.manager;

import com.trendyol.basket.message.ProductPriceChangedMessage;
import com.trendyol.basket.message.ProductStockChangedMessage;

public interface EventManager {
    void sendPriceChangedNotification(ProductPriceChangedMessage productPriceChangedMessage);
    void sendStockChangedNotification(ProductStockChangedMessage productStockChangedMessage);
}
