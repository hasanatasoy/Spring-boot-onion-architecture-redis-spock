package com.trendyol.basket.externalservice.notification;

import com.trendyol.basket.externalservice.notification.request.NotificationProductInfoRequest;

import java.util.List;

public interface NotificationService {
    void sendEmailWhenStockIsCritical(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo);
    void sendEmailWhenPriceDrops(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo);
}
