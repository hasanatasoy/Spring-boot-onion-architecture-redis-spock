package com.trendyol.campaign;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trendyol.basket.exception.GetCampaignException;
import com.trendyol.basket.externalservice.campaign.model.request.GetCampaignRequest;
import com.trendyol.basket.externalservice.campaign.model.response.GetCampaignResponse;
import com.trendyol.basket.externalservice.campaign.CampaignService;
import com.trendyol.basket.model.ApiResponse;
import com.trendyol.basket.model.dto.CampaignDTO;
import com.trendyol.campaign.model.request.CalculateCampaignRequest;
import com.trendyol.campaign.model.request.CampaignProduct;
import com.trendyol.campaign.model.response.CalculatedCampaignResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CampaignServiceImpl implements CampaignService {

    private String campaignServiceUrl = "http://localhost:8081/campaign";
    private final RestTemplate restTemplate;

    public CampaignServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public GetCampaignResponse get(GetCampaignRequest getCampaignRequest) {
       try{
           var url = campaignServiceUrl + "/calculate";
           var calculateCampaignRequest = new CalculateCampaignRequest();
           calculateCampaignRequest.setCustomerId(getCampaignRequest.getCustomerId());
           var campaignProducts = new ArrayList<CampaignProduct>();
           AtomicReference<BigDecimal> basketTotal = new AtomicReference<>(BigDecimal.ZERO);
           getCampaignRequest.getProductInfoDTOList().forEach(getProductInfo -> {
               var campaignProduct = new CampaignProduct();
               campaignProduct.setProductId(getProductInfo.getProductId());
               campaignProduct.setQuantity(getProductInfo.getQuantity());
               var price = getProductInfo.getPrice();
               var quantity = BigDecimal.valueOf(getProductInfo.getQuantity());
               basketTotal.set(basketTotal.get().add(price.multiply(quantity)));
               campaignProducts.add(campaignProduct);
           });
           calculateCampaignRequest.setBasketTotal(basketTotal.get());
           calculateCampaignRequest.setCampaignProducts(campaignProducts);
           var jsonRequest = new Gson().toJson(calculateCampaignRequest);
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           var httpEntity = new HttpEntity<String>(jsonRequest, headers);
           var response = restTemplate.postForObject(url, httpEntity, String.class);
           var responseType = new TypeToken<ApiResponse<CalculatedCampaignResponse>>(){}.getType();
           ApiResponse<CalculatedCampaignResponse> apiResponse = new Gson().fromJson(response, responseType);
           if(apiResponse == null || !apiResponse.isSuccess()){
               throw new GetCampaignException();
           }
           var calculatedCampaignResponse = apiResponse.getData();
           var getCampaignResponse = new GetCampaignResponse();
           var campaignDTOs = new ArrayList<CampaignDTO>();
           calculatedCampaignResponse.getCalculatedCampaigns().forEach(calculatedCampaign -> {
               campaignDTOs.add(new CampaignDTO(calculatedCampaign.getName(), calculatedCampaign.getToBeAppliedPrice()));
           });
           getCampaignResponse.setCustomerId(getCampaignRequest.getCustomerId());
           getCampaignResponse.setCampaignDTOs(campaignDTOs);
           return getCampaignResponse;
       }
       catch (Exception ex){
           throw new GetCampaignException();
       }
    }
}
