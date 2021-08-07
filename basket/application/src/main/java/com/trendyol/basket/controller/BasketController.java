package com.trendyol.basket.controller;

import com.trendyol.basket.model.ApiResponse;
import com.trendyol.basket.model.request.AddToBasketRequest;
import com.trendyol.basket.model.request.UpdateBasketRequest;
import com.trendyol.basket.model.response.AddToBasketResponse;
import com.trendyol.basket.model.response.GetBasketResponse;
import com.trendyol.basket.model.response.UpdateBasketResponse;

public interface BasketController {
    ApiResponse<AddToBasketResponse> add(AddToBasketRequest addToBasketRequest);
    ApiResponse<UpdateBasketResponse> update(UpdateBasketRequest updateBasketRequest);
    ApiResponse<GetBasketResponse> get(long customerId);
}
