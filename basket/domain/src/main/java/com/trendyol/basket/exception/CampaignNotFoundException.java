package com.trendyol.basket.exception;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(){
        super("campaign.not.found");
    }
}
