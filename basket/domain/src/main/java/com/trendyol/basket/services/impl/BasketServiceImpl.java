package com.trendyol.basket.services.impl;

import com.trendyol.basket.entity.Basket;
import com.trendyol.basket.entity.BasketsIdsByProductId;
import com.trendyol.basket.entity.ProductInfo;
import com.trendyol.basket.exception.BasketIdsByProductIdNotFoundException;
import com.trendyol.basket.exception.BasketNotFoundException;
import com.trendyol.basket.repository.BasketRepository;
import com.trendyol.basket.services.BasketService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    public BasketServiceImpl(
            BasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    @Override
    public Basket add(long customerId, long productId,
                       String imageUrl, String title, int quantity, BigDecimal price, BigDecimal oldPrice) {
        var optionalBasket = basketRepository.findByCustomerId(customerId);
        var productInfo = new ProductInfo(productId, imageUrl, title, quantity, price, oldPrice);
        Basket basket;
        if(optionalBasket.isEmpty()){
            basket = new Basket(customerId, productInfo);
        }
        else{
            basket = optionalBasket.get();
            var optProductInfo = basket.getProducts().stream().filter(pi -> pi.getId() == productId).findFirst();
            if(optProductInfo.isEmpty()){
                basket.addItemToBasket(productInfo);
            }
            else{
                var existProductInfo = optProductInfo.get();
                basket.setProductQuantity(existProductInfo.getId(), existProductInfo.getQuantity() + quantity);
            }
        }
        saveBasketIdsByProductId(customerId, productId);
        basketRepository.save(basket);
        return basket;
    }

    @Override
    public Basket update(long customerId, long productId, int quantity) {
        var optionalBasket = basketRepository.findByCustomerId(customerId);
        if(optionalBasket.isEmpty())
            throw new BasketNotFoundException();
        var basket = optionalBasket.get();
        basket.setProductQuantity(productId, quantity);
        basketRepository.save(basket);
        saveBasketIdsByProductId(customerId, productId);
        return basket;
    }

    private void saveBasketIdsByProductId(long customerId, long productId) {
        var optBasketIdsByProductId = basketRepository.findByProductId(productId);
        BasketsIdsByProductId basketsIdsByProductId;
        if(optBasketIdsByProductId.isPresent()){
            basketsIdsByProductId = optBasketIdsByProductId.get();
            basketsIdsByProductId.getBasketIds().add(customerId);
        }
        else{
            var basketIds = new HashSet<Long>();
            basketIds.add(customerId);
            basketsIdsByProductId = new BasketsIdsByProductId(productId, basketIds);
        }
        basketRepository.saveProductBaskets(basketsIdsByProductId);
    }

    @Override
    public Basket get(long customerId) {
        var basket = basketRepository.findByCustomerId(customerId)
                .orElseThrow(BasketNotFoundException::new);
        return basket;
    }

    @Override
    public void addCampaignToBasket(long customerId, String campaignDisplayName, BigDecimal campaignPrice) {
        var optionalBasket = basketRepository.findByCustomerId(customerId);
        if(optionalBasket.isEmpty())
            throw new BasketNotFoundException();
        var basket = optionalBasket.get();
        basket.getBasketInfo().updateGrandTotalWithCampaign(campaignDisplayName, campaignPrice);
        basketRepository.save(basket);
    }

    @Override
    public void clearBasketCampaigns(long customerId) {
        var optionalBasket = basketRepository.findByCustomerId(customerId);
        if(optionalBasket.isEmpty())
            throw new BasketNotFoundException();
        var basket = optionalBasket.get();
        basket.getBasketInfo().clearCampaigns();
        basketRepository.save(basket);
    }

    @Override
    public List<Basket> getByProductId(long productId) {
        var optionalBasketsIdsByProductId = basketRepository.findByProductId(productId);
        if(optionalBasketsIdsByProductId.isEmpty()){
            return new ArrayList<>();
        }
        var basketsIdsByProductId = optionalBasketsIdsByProductId.get();
        var baskets = new ArrayList<Basket>();
        basketsIdsByProductId.getBasketIds().stream().forEach(basketId -> {
            var optBasket = basketRepository.findByCustomerId(basketId);
            optBasket.ifPresent(baskets::add);
        });
        return baskets;
    }

    @Override
    public Basket deleteItemFromBasket(long customerId, long productId) {
        var optionalBasketsIdsByProductId = basketRepository.findByCustomerId(customerId);
        if(optionalBasketsIdsByProductId.isEmpty()){
            throw new BasketNotFoundException();
        }
        var basket = optionalBasketsIdsByProductId.get();
        basket.setProductQuantity(productId, 0);
        basketRepository.save(basket);
        deleteBasketIdFromBasketIdsByProductId(customerId, productId);
        return basket;
    }

    private void deleteBasketIdFromBasketIdsByProductId(long customerId, long productId) {
        var optBasketIdsByProductId = basketRepository.findByProductId(productId);
        BasketsIdsByProductId basketsIdsByProductId;
        if(!optBasketIdsByProductId.isPresent()){
            throw new BasketIdsByProductIdNotFoundException();
        }
        basketsIdsByProductId = optBasketIdsByProductId.get();
        basketsIdsByProductId.getBasketIds().remove(customerId);
        basketRepository.saveProductBaskets(basketsIdsByProductId);
    }
}
