package com.trendyol.basket.entity;

import com.trendyol.basket.exception.BasketValidationException;
import com.trendyol.basket.exception.ProductNotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RedisHash("Basket")
public class Basket {
    @Id
    private long customerId;
    private List<ProductInfo> products;
    private BasketInfo basketInfo;

    public Basket(){}

    public Basket(long customerId, ProductInfo product){
        var isBasketFieldsNotValid = false;
        isBasketFieldsNotValid =
                customerId <= 0L
                || product == null;
        if(isBasketFieldsNotValid)
            throw new BasketValidationException();
        this.customerId = customerId;
        this.products = new ArrayList<>();
        products.add(product);
        basketInfo = new BasketInfo(products);
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }

    public void setBasketInfo(BasketInfo basketInfo) {
        this.basketInfo = basketInfo;
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public BasketInfo getBasketInfo() {
        return basketInfo;
    }

    public void addItemToBasket(ProductInfo productInfo){
        products.add(productInfo);
        basketInfo.updateSubTotal(products);
    }

    public void setProductQuantity(long productId, int quantity) {
        Optional<ProductInfo> optionalProductInfo = products.stream()
                .filter(productInfo -> productInfo.getId() == productId)
                .findFirst();
        if(optionalProductInfo.isEmpty()){
            throw new ProductNotFoundException();
        }
        var productInfo = optionalProductInfo.get();
        if(quantity != 0){
            productInfo.setQuantity(quantity);
        }
        else{
            products.remove(productInfo);
        }
        basketInfo.updateSubTotal(products);
    }
}
