package com.trendyol.basket

import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.BasketsIdsByProductId
import com.trendyol.basket.entity.ProductInfo
import com.trendyol.basket.exception.BasketNotFoundException
import com.trendyol.basket.exception.ProductNotFoundException
import com.trendyol.basket.repository.BasketRepository
import com.trendyol.basket.services.impl.BasketServiceImpl
import spock.lang.Specification

class BasketServiceSpecification extends Specification{

    def "Test add: If basket is null then create new one and calculate subTotal"(){
        setup:
            def customerId = 1L
            def productId = 1L
            def imageUrl = "test.jpg"
            def title = "Product 1"
            def quantity = 3
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.empty()
                findByProductId(productId) >> Optional.empty()
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def insertedBasket = basketService.add(customerId, productId, imageUrl, title, quantity, price, oldPrice)
        then:
            insertedBasket.getProducts().size() == 1
            insertedBasket.getProducts().first().id == productId
            insertedBasket.getProducts().first().imageUrl == imageUrl
            insertedBasket.getProducts().first().oldPrice == oldPrice
            insertedBasket.getProducts().first().price == price
            insertedBasket.getProducts().first().quantity == quantity
            insertedBasket.getProducts().first().title == title
            insertedBasket.getCustomerId() == customerId
            insertedBasket.getBasketInfo().getSubTotal() == price * quantity
    }

    def "Test add: If product is already exist in basket then increase quantity and calculate subTotal"(){
        setup:
            def customerId = 1L
            def productId = 1L
            def imageUrl = "test.jpg"
            def title = "Product 1"
            def quantity = 1
            def toBeAddedQuantity = 3;
            def price = BigDecimal.valueOf(100)
            def oldPrice = BigDecimal.valueOf(150)
            def basket = new Basket(customerId, new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice))
            def basketRepository = Mock(BasketRepository) {
                def basketIds = new HashSet<BasketsIdsByProductId>()
                basketIds.add(customerId)
                def basketIdsByProductId = new BasketsIdsByProductId(productId, basketIds)
                findByProductId(productId) >> Optional.of(basketIdsByProductId)
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def insertedBasket = basketService.add(customerId, productId, imageUrl, title, toBeAddedQuantity, price, oldPrice)
        then:
            insertedBasket.getProducts().size() == 1
            insertedBasket.getProducts().first().quantity == quantity + toBeAddedQuantity
            insertedBasket.getBasketInfo().getSubTotal() == price * (quantity + toBeAddedQuantity)
    }

    def "Test add: If product is unique for exist basket then add item to basket and calculate subTotal"(){
        setup:
            def customerId = 1L
            def productOneId = 1L
            def productTwoId = 2L
            def imageUrl = "test.jpg"
            def title = "Product 1"
            def productOneQuantity = 2
            def productTwoQuantity = 1
            def productOnePrice = BigDecimal.valueOf(100)
            def productTwoPrice = BigDecimal.valueOf(225)
            def productOneOldPrice = BigDecimal.valueOf(135)
            def productTwoOldPrice = BigDecimal.valueOf(288)
            def basket = new Basket(customerId, new ProductInfo(productOneId, imageUrl, title, productOneQuantity, productOnePrice, productOneOldPrice))
            def basketRepository = Mock(BasketRepository) {
                def basketIds = new HashSet<BasketsIdsByProductId>()
                basketIds.add(customerId)
                def basketIdsByProductId = new BasketsIdsByProductId(productTwoId, basketIds)
                findByProductId(productTwoId) >> Optional.of(basketIdsByProductId)
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def insertedBasket = basketService.add(customerId, productTwoId, imageUrl, title, productTwoQuantity, productTwoPrice, productTwoOldPrice)
        then:
            insertedBasket.getProducts().size() == 2
            insertedBasket.getProducts().stream()
                    .filter({ product -> product.id == productOneId })
                    .findFirst()
                    .get().quantity == productOneQuantity
            insertedBasket.getProducts().stream()
                    .filter({ product -> product.id == productTwoId })
                    .findFirst()
                    .get().quantity == productTwoQuantity
            insertedBasket.getBasketInfo().getSubTotal() == (productTwoQuantity * productTwoPrice) + (productOneQuantity * productOnePrice)
    }

    def "Test update: If basket is empty then throw BasketNotFoundException"(){
        setup:
            def customerId = 1L
            def productId = 1L
            def productQuantity = 2
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.empty()
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.update(customerId, productId, productQuantity)
        then:
            thrown BasketNotFoundException
    }

    def "Test update: If basket is not empty then set new product quantity and calculate subTotal"(){
        setup:
            def customerId = 1L
            def productId = 1L
            def quantity = 1
            def newQuantity = 3
            def existsProductPrice = BigDecimal.valueOf(155)
            def basket = new Basket(customerId,
                    new ProductInfo(productId, "imageUrl", "title", quantity, existsProductPrice, BigDecimal.valueOf(155)))
            def basketRepository = Mock(BasketRepository) {
                def basketIds = new HashSet<BasketsIdsByProductId>()
                basketIds.add(customerId)
                def basketIdsByProductId = new BasketsIdsByProductId(productId, basketIds)
                findByProductId(productId) >> Optional.of(basketIdsByProductId)
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def updatedBasket = basketService.update(customerId, productId, newQuantity)
        then:
            updatedBasket.getProducts().size() == 1
            updatedBasket.getProducts().first().quantity == newQuantity
            updatedBasket.getBasketInfo().getSubTotal() == existsProductPrice * newQuantity
    }

    def "Test update: If item to be updated is not in basket then throw ProductNotFoundException"(){
        setup:
        def customerId = 1L
        def productId = 1L
        def productTwoId = 2L
        def basket = new Basket(customerId,
                new ProductInfo(productId, "imageUrl", "title", 2, BigDecimal.valueOf(155), BigDecimal.valueOf(155)))
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.update(customerId, productTwoId, 1)
        then:
            thrown ProductNotFoundException
    }

    def "Test get: If basket is not exist then throw ProductNotFoundException"(){
        setup:
            def customerId = 1L
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.empty()
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.get(customerId)
        then:
            thrown BasketNotFoundException
    }

    def "Test get: If basket is exist then return basket"(){
        setup:
            def customerId = 1L
            def basket = new Basket(customerId,
                    new ProductInfo(1L, "imageUrl", "title", 2, BigDecimal.valueOf(155), BigDecimal.valueOf(155)))
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def existsBasket = basketService.get(customerId)
        then:
            existsBasket.getProducts().size() == 1
            existsBasket.getProducts().first().id == 1L
            existsBasket.getProducts().first().quantity == 2
            existsBasket.getProducts().first().title == "title"
            existsBasket.getProducts().first().imageUrl == "imageUrl"
            existsBasket.getProducts().first().price == BigDecimal.valueOf(155)
            existsBasket.getProducts().first().oldPrice == BigDecimal.valueOf(155)
            existsBasket.getCustomerId() == customerId
            existsBasket.getBasketInfo().getSubTotal() == BigDecimal.valueOf(155) * 2
    }

    def "Test addCampaignToBasket: If basket is not exist then throw BasketNotFoundException"(){
        setup:
            def customerId = 1L
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.empty()
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.addCampaignToBasket(customerId, "test", BigDecimal.valueOf(155))
        then:
            thrown BasketNotFoundException
    }

    def "Test addCampaignToBasket: When campaign applied to basket then calculate grandTotal of basket"(){
        setup:
            def customerId = 1L
            def campaignDisplayName = "Kargo"
            def campaignPrice = BigDecimal.valueOf(4.99)
            def basket = new Basket(customerId,
                    new ProductInfo(1L, "imageUrl", "title", 2, BigDecimal.valueOf(155), BigDecimal.valueOf(155)))
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.addCampaignToBasket(customerId, campaignDisplayName, campaignPrice)
        then:
            basket.getBasketInfo().getGrandTotal() == (2 * BigDecimal.valueOf(155)) + campaignPrice
    }

    def "Test clearBasketCampaigns: If basket is not exist then throw BasketNotFoundException"(){
        setup:
            def customerId = 1L
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.empty()
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.clearBasketCampaigns(customerId)
        then:
            thrown BasketNotFoundException
    }

    def "Test clearBasketCampaigns: Basket campaigns should clear"(){
        setup:
            def customerId = 1L
            def basket = new Basket(customerId,
                    new ProductInfo(1L, "imageUrl", "title", 2, BigDecimal.valueOf(111), BigDecimal.valueOf(233)))
            def basketRepository = Mock(BasketRepository) {
                findByCustomerId(customerId) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            basketService.clearBasketCampaigns(customerId)
        then:
            basket.getBasketInfo().getCampaigns().isEmpty()
    }

    def "Test getByProductId: If baskets not found with productId then return empty list"(){
        setup:
            def productId = 1L
            def basketRepository = Mock(BasketRepository) {
                findByProductId(productId) >> Optional.empty()
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def baskets = basketService.getByProductId(productId)
        then:
            baskets.isEmpty()
    }

    def "Test getByProductId: If baskets found with productId then return baskets"(){
        setup:
            def productId = 1L
            def basket = new Basket(1L,
                    new ProductInfo(productId, "imageUrl", "title", 2, BigDecimal.valueOf(111), BigDecimal.valueOf(233)))
            def basketIds = new HashSet<Long>()
            basketIds.add(1L)
            def basketIdsByProductId = new BasketsIdsByProductId(productId, basketIds)
            def baskets = new ArrayList<Basket>();
            baskets.add(basket);
            def basketRepository = Mock(BasketRepository) {
                findByProductId(productId) >> Optional.of(basketIdsByProductId)
                findByCustomerId(1L) >> Optional.of(basket)
            }
            def basketService = new BasketServiceImpl(basketRepository)
        when:
            def existsBaskets = basketService.getByProductId(productId)
        then:
            existsBaskets.size() == 1
            existsBaskets.first().getProducts().any({ product -> product.getId() == productId })
    }
}
