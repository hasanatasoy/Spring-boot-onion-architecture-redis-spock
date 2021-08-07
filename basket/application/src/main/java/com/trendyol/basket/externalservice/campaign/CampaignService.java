package com.trendyol.basket.externalservice.campaign;

import com.trendyol.basket.externalservice.campaign.model.request.GetCampaignRequest;
import com.trendyol.basket.externalservice.campaign.model.response.GetCampaignResponse;

public interface CampaignService {
    GetCampaignResponse get(GetCampaignRequest getCampaignRequest);
}
