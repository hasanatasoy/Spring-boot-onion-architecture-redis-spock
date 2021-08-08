package com.trendyol.basket.manager;

import com.trendyol.basket.model.request.AddToBasketRequest;
import com.trendyol.basket.model.request.GetBasketRequest;
import com.trendyol.basket.model.request.GetBasketsByProductIdRequest;
import com.trendyol.basket.model.request.UpdateBasketRequest;
import com.trendyol.basket.model.response.AddToBasketResponse;
import com.trendyol.basket.model.response.GetBasketResponse;
import com.trendyol.basket.model.response.UpdateBasketResponse;

import java.util.List;

public interface BasketManager {
    AddToBasketResponse add(AddToBasketRequest addToBasketRequest);
    UpdateBasketResponse update(UpdateBasketRequest updateBasketRequest);
    GetBasketResponse get(GetBasketRequest getBasketRequest);
    List<GetBasketResponse> getByProductId(GetBasketsByProductIdRequest getBasketsByProductIdRequest);
}
