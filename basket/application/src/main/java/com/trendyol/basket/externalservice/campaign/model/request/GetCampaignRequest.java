package com.trendyol.basket.externalservice.campaign.model.request;

import com.trendyol.basket.externalservice.campaign.model.dto.CampaignProductInfoDTO;

import java.util.List;

public class GetCampaignRequest {
    private long customerId;
    private List<CampaignProductInfoDTO> productInfoDTOs;

    public GetCampaignRequest(long customerId, List<CampaignProductInfoDTO> productInfoDTOList) {
        this.customerId = customerId;
        this.productInfoDTOs = productInfoDTOList;
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<CampaignProductInfoDTO> getProductInfoDTOList() {
        return productInfoDTOs;
    }
}
