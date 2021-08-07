package com.trendyol.campaign.exception;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(){
        super("campaign.not.found");
    }
}
