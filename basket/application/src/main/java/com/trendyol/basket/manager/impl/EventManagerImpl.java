package com.trendyol.basket.manager.impl;

import com.trendyol.basket.entity.Basket;
import com.trendyol.basket.entity.ProductInfo;
import com.trendyol.basket.externalservice.customer.CustomerService;
import com.trendyol.basket.externalservice.customer.request.GetCustomerRequest;
import com.trendyol.basket.externalservice.customer.response.GetCustomerResponse;
import com.trendyol.basket.externalservice.notification.NotificationService;
import com.trendyol.basket.externalservice.notification.model.request.NotificationProductInfoRequest;
import com.trendyol.basket.externalservice.notification.model.request.StockNotificationType;
import com.trendyol.basket.manager.EventManager;
import com.trendyol.basket.message.ProductPriceChangedMessage;
import com.trendyol.basket.message.ProductStockChangedMessage;
import com.trendyol.basket.services.BasketService;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventManagerImpl implements EventManager {

    private static final int notificationStockChangeLimit = 3;
    private final CustomerService customerService;
    private final NotificationService notificationService;
    private final BasketService basketService;

    public EventManagerImpl(
            NotificationService notificationService,
            BasketService basketService,
            CustomerService customerService){
        this.notificationService = notificationService;
        this.basketService = basketService;
        this.customerService = customerService;
    }

    @Override
    @KafkaListener(topics = "ProductPriceChanged", groupId = "price",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void sendPriceChangedNotification(@Payload ProductPriceChangedMessage productPriceChangedMessage) {
        if(productPriceChangedMessage.getPrice().compareTo(productPriceChangedMessage.getOldPrice()) > 0){
            return;
        }
        var baskets = basketService.getByProductId(productPriceChangedMessage.getProductId());
        if(baskets.size() == 0){
            return;
        }
        var tempProduct = getTempProductFromBasket(productPriceChangedMessage.getProductId(), baskets);
        var notificationProductInfoRequest = new NotificationProductInfoRequest(
                tempProduct.getQuantity(),
                productPriceChangedMessage.getPrice(),
                productPriceChangedMessage.getOldPrice(),
                tempProduct.getTitle()
        );
        var customerEmails = baskets.stream()
                .map(Basket::getCustomerId)
                .map(customerId -> customerService.get(new GetCustomerRequest(customerId)))
                .map(GetCustomerResponse::getEmail)
                .collect(Collectors.toList());
        notificationService.sendEmailWhenPriceDrops(customerEmails, notificationProductInfoRequest);
    }

    @Override
    @KafkaListener(topics = "ProductStockChanged", groupId = "stock",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void sendStockChangedNotification(@Payload ProductStockChangedMessage productStockChangedMessage) {
        if(productStockChangedMessage.getQuantity() > notificationStockChangeLimit){
            return;
        }
        var baskets = basketService.getByProductId(productStockChangedMessage.getProductId());
        if(baskets.size() == 0){
            return;
        }
        var tempProduct = getTempProductFromBasket(productStockChangedMessage.getProductId(), baskets);
        var notificationProductInfoRequest = new NotificationProductInfoRequest(
                productStockChangedMessage.getQuantity(),
                tempProduct.getPrice(),
                tempProduct.getOldPrice(),
                tempProduct.getTitle()
        );
        var customerEmails = baskets.stream()
                .map(Basket::getCustomerId)
                .map(customerId -> customerService.get(new GetCustomerRequest(customerId)))
                .map(GetCustomerResponse::getEmail)
                .collect(Collectors.toList());
        StockNotificationType stockType = StockNotificationType.Critic;
        if(productStockChangedMessage.getQuantity() == 0){
            stockType = StockNotificationType.UnAvaiable;
        }
        notificationService.sendEmailWhenStockIsCriticalOrUnAvaiable(customerEmails, notificationProductInfoRequest, stockType);
    }

    @NotNull
    private ProductInfo getTempProductFromBasket(long productId, List<Basket> baskets) {
        return baskets.stream()
                .findFirst()
                .get()
                .getProducts()
                .stream()
                .filter(productInfo -> productInfo.getId() == productId)
                .findFirst()
                .get();
    }
}
