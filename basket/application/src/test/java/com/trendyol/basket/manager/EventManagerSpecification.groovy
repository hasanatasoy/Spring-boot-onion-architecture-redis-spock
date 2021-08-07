package com.trendyol.basket.manager

import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.ProductInfo
import com.trendyol.basket.externalservice.customer.CustomerService
import com.trendyol.basket.externalservice.customer.request.GetCustomerRequest
import com.trendyol.basket.externalservice.customer.response.GetCustomerResponse
import com.trendyol.basket.externalservice.notification.NotificationService
import com.trendyol.basket.manager.impl.EventManagerImpl
import com.trendyol.basket.message.ProductPriceChangedMessage
import com.trendyol.basket.message.ProductStockChangedMessage
import com.trendyol.basket.services.BasketService
import spock.lang.Specification

class EventManagerSpecification extends Specification{

    def "When price greater than old price then early return this means no exception thrown"(){
        setup:
            def priceChangedMessage = new ProductPriceChangedMessage()
            priceChangedMessage.setProductId(3L)
            priceChangedMessage.setOldPrice(BigDecimal.valueOf(100))
            priceChangedMessage.setPrice(BigDecimal.valueOf(101))
            def eventManager = new EventManagerImpl(null, null, null)
        when:
            eventManager.sendPriceChangedNotification(priceChangedMessage)
        then:
            noExceptionThrown()
    }

    def "When price changed and basket size equal zero then early return this means no exception thrown"(){
        setup:
            def priceChangedMessage = new ProductPriceChangedMessage()
            priceChangedMessage.setProductId(3L)
            priceChangedMessage.setOldPrice(BigDecimal.valueOf(100))
            priceChangedMessage.setPrice(BigDecimal.valueOf(101))
            def basketService = Mock(BasketService){
                getByProductId(3L) >> new ArrayList<Basket>()
            }
            def eventManager = new EventManagerImpl(null, basketService, null)
        when:
            eventManager.sendPriceChangedNotification(priceChangedMessage)
        then:
            noExceptionThrown()
    }

    def "When price changed and basket size greater than zero then should be send email to customer: this mean no exception thrown"(){
        setup:
            def priceChangedMessage = new ProductPriceChangedMessage()
            priceChangedMessage.setProductId(3L)
            priceChangedMessage.setOldPrice(BigDecimal.valueOf(100))
            priceChangedMessage.setPrice(BigDecimal.valueOf(88))
            def notificationService = Mock(NotificationService){
                sendEmailWhenPriceDrops(_) >> {}
            }
            def basketService = Mock(BasketService){
                def basket = new Basket(1L,
                        new ProductInfo(
                                priceChangedMessage.getProductId(),
                                "test",
                                "test2",
                                5,
                                BigDecimal.valueOf(100),
                                BigDecimal.valueOf(150))
                )
                def baskets = new ArrayList<Basket>()
                baskets.add(basket)
                getByProductId(3L) >> baskets
            }
            def customerService = Mock(CustomerService){
                get(_ as GetCustomerRequest) >> new GetCustomerResponse(1L, "atasoyhasan96@gmail.com", "Hasan", "Atasoy")
            }
            def eventManager = new EventManagerImpl(notificationService, basketService, customerService)
        when:
            eventManager.sendPriceChangedNotification(priceChangedMessage)
        then:
            noExceptionThrown()
    }

    def "When stock greater than notification limit then early return this means no exception thrown"(){
        setup:
            def stockChangedMessage = new ProductStockChangedMessage()
            stockChangedMessage.setProductId(3L)
            stockChangedMessage.setOldQuantity(5)
            stockChangedMessage.setQuantity(4)
            def eventManager = new EventManagerImpl(null, null, null)
        when:
            eventManager.sendStockChangedNotification(stockChangedMessage)
        then:
            noExceptionThrown()
    }

    def "When stock changed and basket size equal zero then early return this means no exception thrown"(){
        setup:
            def stockChangedMessage = new ProductStockChangedMessage()
            stockChangedMessage.setProductId(3L)
            stockChangedMessage.setOldQuantity(5)
            stockChangedMessage.setQuantity(2)
            def basketService = Mock(BasketService){
                getByProductId(3L) >> new ArrayList<Basket>()
            }
            def eventManager = new EventManagerImpl(null, basketService, null)
        when:
            eventManager.sendStockChangedNotification(stockChangedMessage)
        then:
            noExceptionThrown()
    }

    def "When stock changed and basket size greater than zero then should be send email to customer: this mean no exception thrown"(){
        setup:
            def stockChangedMessage = new ProductStockChangedMessage()
            stockChangedMessage.setProductId(3L)
            stockChangedMessage.setOldQuantity(5)
            stockChangedMessage.setQuantity(1)
            def notificationService = Mock(NotificationService){
                sendEmailWhenPriceDrops(_) >> {}
            }
            def basketService = Mock(BasketService){
                def basket = new Basket(1L,
                        new ProductInfo(
                                stockChangedMessage.getProductId(),
                                "test",
                                "test2",
                                5,
                                BigDecimal.valueOf(100),
                                BigDecimal.valueOf(150))
                )
                def baskets = new ArrayList<Basket>()
                baskets.add(basket)
                getByProductId(3L) >> baskets
            }
            def customerService = Mock(CustomerService){
            get(_ as GetCustomerRequest) >> new GetCustomerResponse(1L, "atasoyhasan96@gmail.com", "Hasan", "Atasoy")
        }
            def eventManager = new EventManagerImpl(notificationService, basketService, customerService)
        when:
            eventManager.sendStockChangedNotification(stockChangedMessage)
        then:
            noExceptionThrown()
    }
}
