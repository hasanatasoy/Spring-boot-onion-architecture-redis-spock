package com.trendyol.basket.entity;

import com.trendyol.basket.exception.BasketInfoValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BasketInfo {
    private BigDecimal subTotal;
    private List<BasketCampaign> basketCampaigns;
    private BigDecimal grandTotal;

    public BasketInfo(){}

    public BasketInfo(List<ProductInfo> productInfos){
        var isBasketInfoFieldsNotValid = false;
        isBasketInfoFieldsNotValid =
                productInfos == null || productInfos.size() == 0;
        if(isBasketInfoFieldsNotValid)
            throw new BasketInfoValidationException();
        updateSubTotal(productInfos);
        basketCampaigns = new ArrayList<>();
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<BasketCampaign> getBasketCampaigns() {
        return basketCampaigns;
    }

    public void setBasketCampaigns(List<BasketCampaign> basketCampaigns) {
        this.basketCampaigns = basketCampaigns;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public List<BasketCampaign> getCampaigns() {
        return basketCampaigns;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void updateSubTotal(List<ProductInfo> productInfoList){
        subTotal = productInfoList.stream()
                .map(productInfo -> productInfo.getPrice().multiply(BigDecimal.valueOf(productInfo.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateGrandTotalWithCampaign(String campaignDisplayName, BigDecimal campaignPrice){
        if(campaignDisplayName == null || campaignDisplayName.isEmpty()){
            throw new BasketInfoValidationException();
        }
        addCampaign(campaignDisplayName, campaignPrice);
        grandTotal = basketCampaigns.stream()
                .map(BasketCampaign::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add).add(subTotal);
    }

    private void addCampaign(String displayName, BigDecimal price){
        if(basketCampaigns == null){
            basketCampaigns = new ArrayList<>();
        }
        var campaign = new BasketCampaign(displayName, price);
        basketCampaigns.add(campaign);
    }

    public void clearCampaigns(){
        if(basketCampaigns != null){
            basketCampaigns.clear();
        }
        else{
            basketCampaigns = new ArrayList<>();
        }
    }
}
