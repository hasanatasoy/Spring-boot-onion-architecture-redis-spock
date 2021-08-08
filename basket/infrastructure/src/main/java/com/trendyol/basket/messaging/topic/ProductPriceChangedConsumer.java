package com.trendyol.basket.messaging.topic;

import com.trendyol.basket.externalservice.customer.CustomerService;
import com.trendyol.basket.externalservice.customer.request.GetCustomerRequest;
import com.trendyol.basket.externalservice.customer.response.GetCustomerResponse;
import com.trendyol.basket.externalservice.notification.NotificationService;
import com.trendyol.basket.externalservice.notification.model.request.NotificationProductInfoRequest;
import com.trendyol.basket.externalservice.product.ProductService;
import com.trendyol.basket.externalservice.product.model.request.GetProductRequest;
import com.trendyol.basket.manager.BasketManager;
import com.trendyol.basket.messaging.Consumer;
import com.trendyol.basket.messaging.model.ProductPriceChangedMessage;
import com.trendyol.basket.model.dto.BasketDTO;
import com.trendyol.basket.model.request.GetBasketsByProductIdRequest;
import com.trendyol.basket.model.response.GetBasketResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductPriceChangedConsumer implements Consumer<ProductPriceChangedMessage> {

    private final NotificationService notificationService;
    private final BasketManager basketManager;
    private final ProductService productService;
    private final CustomerService customerService;

    public ProductPriceChangedConsumer(
            NotificationService notificationService,
            BasketManager basketManager,
            ProductService productService,
            CustomerService customerService) {
        this.notificationService = notificationService;
        this.basketManager = basketManager;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    @KafkaListener(topics = "ProductPriceChanged", groupId = "basket",
            containerFactory = "getProductPriceChangedListenerFactory")
    public void consume(ProductPriceChangedMessage productPriceChangedMessage) {
        if(productPriceChangedMessage.getPrice().compareTo(productPriceChangedMessage.getOldPrice()) > 0){
            return;
        }
        var baskets = basketManager.getByProductId(new GetBasketsByProductIdRequest(productPriceChangedMessage.getProductId()));
        if(baskets.size() == 0){
            return;
        }
        var getProductResponse = productService.get(new GetProductRequest(productPriceChangedMessage.getProductId()));
        var notificationProductInfoRequest = new NotificationProductInfoRequest(
                getProductResponse.getProductInfoDto().getQuantity(),
                productPriceChangedMessage.getPrice(),
                productPriceChangedMessage.getOldPrice(),
                getProductResponse.getProductInfoDto().getTitle()
        );
        var customerEmails = baskets.stream()
                .map(GetBasketResponse::getBasketDTO)
                .map(BasketDTO::getCustomerId)
                .map(customerId -> customerService.get(new GetCustomerRequest(customerId)))
                .map(GetCustomerResponse::getEmail)
                .collect(Collectors.toList());
        notificationService.sendEmailWhenPriceDrops(customerEmails, notificationProductInfoRequest);
    }
}
