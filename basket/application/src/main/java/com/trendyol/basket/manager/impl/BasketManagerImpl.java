package com.trendyol.basket.manager.impl;

import com.trendyol.basket.converter.impl.BasketDtoConverter;
import com.trendyol.basket.converter.impl.CampaignProductInfoDtoConverter;
import com.trendyol.basket.entity.ProductInfo;
import com.trendyol.basket.exception.ProductStockValidationException;
import com.trendyol.basket.externalservice.campaign.CampaignService;
import com.trendyol.basket.externalservice.campaign.model.request.GetCampaignRequest;
import com.trendyol.basket.externalservice.campaign.model.response.GetCampaignResponse;
import com.trendyol.basket.externalservice.product.ProductService;
import com.trendyol.basket.externalservice.product.model.request.GetProductRequest;
import com.trendyol.basket.manager.BasketManager;
import com.trendyol.basket.model.request.*;
import com.trendyol.basket.model.response.AddToBasketResponse;
import com.trendyol.basket.model.response.DeleteItemFromBasketResponse;
import com.trendyol.basket.model.response.GetBasketResponse;
import com.trendyol.basket.model.response.UpdateBasketResponse;
import com.trendyol.basket.services.BasketService;
import com.trendyol.basket.validator.impl.AddToBasketRequestValidator;
import com.trendyol.basket.validator.impl.GetBasketRequestValidator;
import com.trendyol.basket.validator.impl.UpdateBasketRequestValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketManagerImpl implements BasketManager {
    private final BasketService basketService;
    private final ProductService productService;
    private final CampaignService campaignService;
    private final UpdateBasketRequestValidator updateBasketRequestValidator;
    private final AddToBasketRequestValidator addToBasketRequestValidator;
    private final GetBasketRequestValidator getBasketRequestValidator;
    private final BasketDtoConverter basketDtoConverter;
    private final CampaignProductInfoDtoConverter campaignProductInfoDtoConverter;

    public BasketManagerImpl(
            BasketService basketService,
            ProductService productService,
            CampaignService campaignService,
            UpdateBasketRequestValidator updateBasketRequestValidator,
            AddToBasketRequestValidator addToBasketRequestValidator,
            GetBasketRequestValidator getBasketRequestValidator,
            BasketDtoConverter basketDtoConverter,
            CampaignProductInfoDtoConverter campaignProductInfoDtoConverter){
        this.basketService = basketService;
        this.productService = productService;
        this.campaignService = campaignService;
        this.updateBasketRequestValidator = updateBasketRequestValidator;
        this.addToBasketRequestValidator = addToBasketRequestValidator;
        this.getBasketRequestValidator = getBasketRequestValidator;
        this.basketDtoConverter = basketDtoConverter;
        this.campaignProductInfoDtoConverter = campaignProductInfoDtoConverter;
    }

    @Override
    public AddToBasketResponse add(AddToBasketRequest addToBasketRequest) {
        addToBasketRequestValidator.validate(addToBasketRequest);
        var getProductRequest = new GetProductRequest(addToBasketRequest.getProductId());
        var productResponse = productService.get(getProductRequest);
        validateProductStock(productResponse.getProductInfoDto().getQuantity(), addToBasketRequest.getQuantity());
        var basket = basketService.add(
                addToBasketRequest.getCustomerId(),
                productResponse.getProductInfoDto().getId(),
                productResponse.getProductInfoDto().getImageUrl(),
                productResponse.getProductInfoDto().getTitle(),
                addToBasketRequest.getQuantity(),
                productResponse.getProductInfoDto().getPrice(),
                productResponse.getProductInfoDto().getOldPrice());
        var getCampaignRequest = prepareGetCampaignRequest(basket.getCustomerId(), basket.getProducts());
        var campaignResponse = campaignService.get(getCampaignRequest);
        UpdateBasketInfoWithCampaign(campaignResponse);
        return new AddToBasketResponse();
    }

    @Override
    public UpdateBasketResponse update(UpdateBasketRequest updateBasketRequest) {
        updateBasketRequestValidator.validate(updateBasketRequest);
        var getProductRequest = new GetProductRequest(updateBasketRequest.getProductId());
        var productResponse = productService.get(getProductRequest);
        validateProductStock(productResponse.getProductInfoDto().getQuantity(), updateBasketRequest.getQuantity());
        var basket = basketService.update(
                updateBasketRequest.getCustomerId(),
                updateBasketRequest.getProductId(),
                updateBasketRequest.getQuantity());
        var getCampaignRequest = prepareGetCampaignRequest(basket.getCustomerId(), basket.getProducts());
        var campaignResponse = campaignService.get(getCampaignRequest);
        UpdateBasketInfoWithCampaign(campaignResponse);
        return new UpdateBasketResponse();
    }

    private void validateProductStock(int existsStock, int stockToBeAdded) {
        if(existsStock < stockToBeAdded) {
            throw new ProductStockValidationException();
        }
    }

    private GetCampaignRequest prepareGetCampaignRequest(long customerId, List<ProductInfo> productInfos){
        var getCampaignProductDTOs = productInfos.stream()
                .map(campaignProductInfoDtoConverter::convert)
                .collect(Collectors.toList());
        var getCampaignRequest = new GetCampaignRequest(customerId, getCampaignProductDTOs);
        return getCampaignRequest;
    }

    private void UpdateBasketInfoWithCampaign(GetCampaignResponse getCampaignResponse) {
        basketService.clearBasketCampaigns(getCampaignResponse.getCustomerId());
        getCampaignResponse.getCampaignDTOs().forEach(campaignDTO ->{
            basketService.addCampaignToBasket(getCampaignResponse.getCustomerId(), campaignDTO.getDisplayName(), campaignDTO.getPrice());
        });
    }

    @Override
    public GetBasketResponse get(GetBasketRequest getBasketRequest) {
        getBasketRequestValidator.validate(getBasketRequest);
        var basket = basketService.get(getBasketRequest.getCustomerId());
        var basketDTO = basketDtoConverter.convert(basket);
        return new GetBasketResponse(basketDTO);
    }

    @Override
    public List<GetBasketResponse> getByProductId(GetBasketsByProductIdRequest getBasketsByProductIdRequest) {
        //TODO: validate request
        var baskets = basketService.getByProductId(getBasketsByProductIdRequest.getProductId());
        var basketResponses = baskets.stream()
                .map(basketDtoConverter::convert)
                .map(GetBasketResponse::new)
                .collect(Collectors.toList());
        return basketResponses;
    }

    @Override
    public DeleteItemFromBasketResponse delete(DeleteItemFromBasketRequest deleteItemFromBasketRequest) {
        //TODO: validate request and write unit tests
        var basket = basketService.deleteItemFromBasket(deleteItemFromBasketRequest.getCustomerId(), deleteItemFromBasketRequest.getProductId());
        var deleteItemFromBasketResponse = new DeleteItemFromBasketResponse();
        var getCampaignRequest = prepareGetCampaignRequest(deleteItemFromBasketRequest.getCustomerId(), basket.getProducts());
        var campaignResponse = campaignService.get(getCampaignRequest);
        UpdateBasketInfoWithCampaign(campaignResponse);
        return deleteItemFromBasketResponse;
    }
}