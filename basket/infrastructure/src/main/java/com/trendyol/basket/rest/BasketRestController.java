package com.trendyol.basket.rest;

import com.trendyol.basket.controller.BasketController;
import com.trendyol.basket.manager.BasketManager;
import com.trendyol.basket.model.ApiResponse;
import com.trendyol.basket.model.request.AddToBasketRequest;
import com.trendyol.basket.model.request.DeleteItemFromBasketRequest;
import com.trendyol.basket.model.request.GetBasketRequest;
import com.trendyol.basket.model.request.UpdateBasketRequest;
import com.trendyol.basket.model.response.AddToBasketResponse;
import com.trendyol.basket.model.response.DeleteItemFromBasketResponse;
import com.trendyol.basket.model.response.GetBasketResponse;
import com.trendyol.basket.model.response.UpdateBasketResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("basket")
public class BasketRestController implements BasketController {

    private BasketManager basketManager;

    public BasketRestController(BasketManager basketManager){
        this.basketManager = basketManager;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @Override
    public ApiResponse<AddToBasketResponse> add(@RequestBody AddToBasketRequest addToBasketRequest) {
        var response = basketManager.add(addToBasketRequest);
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @Override
    public ApiResponse<UpdateBasketResponse> update(@RequestBody UpdateBasketRequest updateBasketRequest) {
        var response = basketManager.update(updateBasketRequest);
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    @Override
    public ApiResponse<GetBasketResponse> get(@PathVariable long customerId) {
        var getBasketRequest = new GetBasketRequest(customerId);
        var response = basketManager.get(getBasketRequest);
        return ApiResponse.ApiResponseBuilderWithData
                .builder()
                .setData(response)
                .build();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @Override
    public ApiResponse<DeleteItemFromBasketResponse> delete(@RequestBody DeleteItemFromBasketRequest deleteItemFromBasketRequest) {
        var response = basketManager.delete(deleteItemFromBasketRequest);
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }
}
