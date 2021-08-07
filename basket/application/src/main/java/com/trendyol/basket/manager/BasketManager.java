package com.trendyol.basket.manager;

import com.trendyol.basket.model.request.AddToBasketRequest;
import com.trendyol.basket.model.request.GetBasketRequest;
import com.trendyol.basket.model.request.UpdateBasketRequest;
import com.trendyol.basket.model.response.AddToBasketResponse;
import com.trendyol.basket.model.response.GetBasketResponse;
import com.trendyol.basket.model.response.UpdateBasketResponse;

public interface BasketManager {
    AddToBasketResponse add(AddToBasketRequest addToBasketRequest);
    UpdateBasketResponse update(UpdateBasketRequest updateBasketRequest);
    GetBasketResponse get(GetBasketRequest getBasketRequest);
}
