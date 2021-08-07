package com.trendyol.basket.rest;

import com.trendyol.basket.controller.BasketController;
import com.trendyol.basket.manager.BasketManager;
import com.trendyol.basket.model.ApiResponse;
import com.trendyol.basket.model.request.AddToBasketRequest;
import com.trendyol.basket.model.request.GetBasketRequest;
import com.trendyol.basket.model.request.UpdateBasketRequest;
import com.trendyol.basket.model.response.AddToBasketResponse;
import com.trendyol.basket.model.response.GetBasketResponse;
import com.trendyol.basket.model.response.UpdateBasketResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("basket")
public class BasketRestController implements BasketController {

    private BasketManager basketManager;

    public BasketRestController(BasketManager basketManager){
        this.basketManager = basketManager;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @Override
    public ApiResponse<AddToBasketResponse> add(AddToBasketRequest addToBasketRequest) {
        var response = basketManager.add(addToBasketRequest);
        return ApiResponse.ApiResponseBuilderWithData
                .builder()
                .setData(response)
                .build();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @Override
    public ApiResponse<UpdateBasketResponse> update(UpdateBasketRequest updateBasketRequest) {
        var response = basketManager.update(updateBasketRequest);
        return ApiResponse.ApiResponseBuilderWithData
                .builder()
                .setData(response)
                .build();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    @Override
    public ApiResponse<GetBasketResponse> get(long customerId) {
        var getBasketRequest = new GetBasketRequest(customerId);
        var response = basketManager.get(getBasketRequest);
        return ApiResponse.ApiResponseBuilderWithData
                .builder()
                .setData(response)
                .build();
    }
}
