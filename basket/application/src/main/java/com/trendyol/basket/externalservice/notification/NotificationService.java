package com.trendyol.basket.externalservice.notification;

import com.trendyol.basket.externalservice.notification.model.request.NotificationProductInfoRequest;
import com.trendyol.basket.externalservice.notification.model.request.StockNotificationType;

import java.util.List;

public interface NotificationService {
    void sendEmailWhenStockIsCriticalOrUnAvaiable(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo, StockNotificationType type);
    void sendEmailWhenPriceDrops(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo);
}
