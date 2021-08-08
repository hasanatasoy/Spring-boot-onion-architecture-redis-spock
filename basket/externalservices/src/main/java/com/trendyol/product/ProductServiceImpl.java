package com.trendyol.product;

import com.trendyol.basket.exception.GetProductException;
import com.trendyol.basket.externalservice.product.ProductService;
import com.trendyol.basket.externalservice.product.model.request.GetProductRequest;
import com.trendyol.basket.externalservice.product.model.response.GetProductResponse;
import com.trendyol.basket.model.ApiResponse;
import com.trendyol.basket.model.dto.ProductInfoDTO;
import com.trendyol.product.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService {

    private String productServiceUrl = "http://localhost::8082";
    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public GetProductResponse get(GetProductRequest getProductRequest) {
        var url = productServiceUrl + "/" + getProductRequest.getProductId();
        var response = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ApiResponse<Product>>(){});
        var apiResponse = response.getBody();
        if(apiResponse != null || !apiResponse.isSuccess()){
            throw new GetProductException();
        }
        var product = apiResponse.getData();
        var getProductResponse = new GetProductResponse();
        var productDTO = new ProductInfoDTO();
        productDTO.setId(product.getId());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setOldPrice(product.getOldPrice());
        productDTO.setTitle(product.getTitle());
        productDTO.setImageUrl(product.getImageUrl());
        getProductResponse.setProductInfoDto(productDTO);
        return getProductResponse;
    }
}
