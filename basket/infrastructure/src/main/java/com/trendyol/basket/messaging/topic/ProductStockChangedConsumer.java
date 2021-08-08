package com.trendyol.basket.messaging.topic;

import com.trendyol.basket.entity.Basket;
import com.trendyol.basket.externalservice.customer.CustomerService;
import com.trendyol.basket.externalservice.customer.request.GetCustomerRequest;
import com.trendyol.basket.externalservice.customer.response.GetCustomerResponse;
import com.trendyol.basket.externalservice.notification.NotificationService;
import com.trendyol.basket.externalservice.notification.model.request.NotificationProductInfoRequest;
import com.trendyol.basket.externalservice.notification.model.request.StockNotificationType;
import com.trendyol.basket.externalservice.product.ProductService;
import com.trendyol.basket.externalservice.product.model.request.GetProductRequest;
import com.trendyol.basket.manager.BasketManager;
import com.trendyol.basket.messaging.Consumer;
import com.trendyol.basket.messaging.model.ProductStockChangedMessage;
import com.trendyol.basket.model.dto.BasketDTO;
import com.trendyol.basket.model.request.GetBasketsByProductIdRequest;
import com.trendyol.basket.model.response.GetBasketResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductStockChangedConsumer implements Consumer<ProductStockChangedMessage> {

    private static final int notificationStockChangeLimit = 3;
    private final CustomerService customerService;
    private final NotificationService notificationService;
    private final BasketManager basketManager;
    private final ProductService productService;

    public ProductStockChangedConsumer(
            CustomerService customerService,
            NotificationService notificationService,
            BasketManager basketManager,
            ProductService productService) {
        this.customerService = customerService;
        this.notificationService = notificationService;
        this.basketManager = basketManager;
        this.productService = productService;
    }

    @Override
    @KafkaListener(topics = "ProductStockChanged", groupId = "basket",
            containerFactory = "getProductStockChangedListenerFactory")
    public void consume(@Payload ProductStockChangedMessage productStockChangedMessage) {
        if(productStockChangedMessage.getQuantity() > notificationStockChangeLimit){
            return;
        }
        if(productStockChangedMessage.getOldQuantity() < productStockChangedMessage.getQuantity()){
            return;
        }
        var baskets = basketManager.getByProductId(new GetBasketsByProductIdRequest(productStockChangedMessage.getProductId()));
        if(baskets.size() == 0){
            return;
        }
        var getProductResponse = productService.get(new GetProductRequest(productStockChangedMessage.getProductId()));
        var notificationProductInfoRequest = new NotificationProductInfoRequest(
                productStockChangedMessage.getQuantity(),
                getProductResponse.getProductInfoDto().getPrice(),
                getProductResponse.getProductInfoDto().getOldPrice(),
                getProductResponse.getProductInfoDto().getTitle()
        );
        var customerEmails = baskets.stream()
                .map(GetBasketResponse::getBasketDTO)
                .map(BasketDTO::getCustomerId)
                .map(customerId -> customerService.get(new GetCustomerRequest(customerId)))
                .map(GetCustomerResponse::getEmail)
                .collect(Collectors.toList());
        StockNotificationType stockType = StockNotificationType.Critic;
        if(productStockChangedMessage.getQuantity() == 0){
            stockType = StockNotificationType.UnAvaiable;
        }
        notificationService.sendEmailWhenStockIsCriticalOrUnAvaiable(customerEmails, notificationProductInfoRequest, stockType);
    }
}
