package com.trendyol.product.controller;

import com.trendyol.product.base.ApiResponse;
import com.trendyol.product.model.UpdatePriceRequest;
import com.trendyol.product.model.UpdateStockRequest;
import com.trendyol.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(value = "update/price", method = RequestMethod.POST)
    public ApiResponse updatePrice(@RequestBody UpdatePriceRequest updatePriceRequest){
        productService.updatePrice(updatePriceRequest.getProductId(), updatePriceRequest.getPrice());
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }

    @RequestMapping(value = "update/stock", method = RequestMethod.POST)
    public ApiResponse updateStock(@RequestBody UpdateStockRequest updateStockRequest){
        productService.updateStock(updateStockRequest.getProductId(), updateStockRequest.getQuantity());
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiResponse get(@PathVariable long id){
        var product = productService.get(id);
        return ApiResponse.ApiResponseBuilderWithData
                .builder()
                .setData(product)
                .build();
    }
}
