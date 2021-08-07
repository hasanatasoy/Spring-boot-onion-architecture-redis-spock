package com.trendyol.campaign.controller;

import com.trendyol.campaign.base.ApiResponse;
import com.trendyol.campaign.model.dto.CalculatedCampaign;
import com.trendyol.campaign.model.request.CalculateCampaignRequest;
import com.trendyol.campaign.model.request.UpdateAndCreateCampaignRequest;
import com.trendyol.campaign.model.response.CalculatedCampaignResponse;
import com.trendyol.campaign.service.CampaignService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @RequestMapping(value = "calculate", method = RequestMethod.POST)
    public ApiResponse calculateCampaign(@RequestBody CalculateCampaignRequest calculateCampaignRequest){
        var calculatedCampaign = campaignService.calculateCargoPrice(calculateCampaignRequest.getBasketTotal());
        var calculatedCampaignResponse = new CalculatedCampaignResponse();
        calculatedCampaignResponse.setCustomerId(calculateCampaignRequest.getCustomerId());
        var calculatedCampaigns = new ArrayList<CalculatedCampaign>();
        calculatedCampaigns.add(calculatedCampaign);
        calculatedCampaignResponse.setCalculatedCampaigns(calculatedCampaigns);
        return ApiResponse.ApiResponseBuilderWithData
                .builder()
                .setData(calculatedCampaignResponse)
                .build();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ApiResponse updateCampaign(@RequestBody UpdateAndCreateCampaignRequest updateCampaignRequest){
        campaignService.updateCampaign(
                        updateCampaignRequest.getId(),
                        updateCampaignRequest.getName(),
                        updateCampaignRequest.getThreshold(),
                        updateCampaignRequest.getToBeAppliedPrice(),
                        updateCampaignRequest.getActualPrice());
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ApiResponse CalculateCampaign(@RequestBody UpdateAndCreateCampaignRequest createCampaignRequest){
        campaignService.createCampaign(
                createCampaignRequest.getName(),
                createCampaignRequest.getThreshold(),
                createCampaignRequest.getToBeAppliedPrice(),
                createCampaignRequest.getActualPrice());
        return ApiResponse.ApiResponseBuilder
                .builder()
                .build();
    }
}
