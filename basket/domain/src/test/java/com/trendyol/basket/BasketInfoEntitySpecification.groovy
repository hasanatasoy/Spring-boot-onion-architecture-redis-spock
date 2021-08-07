package com.trendyol.basket

import com.trendyol.basket.entity.BasketCampaign
import com.trendyol.basket.entity.BasketInfo
import com.trendyol.basket.entity.ProductInfo
import com.trendyol.basket.exception.BasketInfoValidationException
import spock.lang.Specification

class BasketInfoEntitySpecification extends Specification{

    def "When productInfos null creating basket info then throw BasketInfoValidationException"(){
        setup:
            def productInfos = null
        when:
            new BasketInfo(productInfos)
        then:
            thrown BasketInfoValidationException
    }

    def "When productInfos size equal zero creating basket info then throw BasketInfoValidationException"(){
        setup:
            def productInfos = new ArrayList<ProductInfo>()
        when:
            new BasketInfo(productInfos)
        then:
            thrown BasketInfoValidationException
    }

    def "Sub total should be calculated by products when basket info created"(){
        setup:
            def productInfos = new ArrayList<ProductInfo>()
            def productInfo1Price = BigDecimal.valueOf(120)
            def productInfo2Price = BigDecimal.valueOf(160)
            def productInfo1Quantity = 2
            def productInfo2Quantity = 4
            def productInfo1 = new ProductInfo(2L, "test", "test2", productInfo1Quantity, productInfo1Price, BigDecimal.valueOf(125))
            def productInfo2 = new ProductInfo(1L, "test", "test2", productInfo2Quantity, productInfo2Price, BigDecimal.valueOf(255))
            productInfos.add(productInfo1)
            productInfos.add(productInfo2)
        when:
            def basketInfo = new BasketInfo(productInfos)
        then:
            basketInfo.getSubTotal() == (productInfo1Quantity * productInfo1Price) + (productInfo2Quantity * productInfo2Price)
    }

    def "Should update grand total with added new campaign for only one campaign"(){
        setup:
            def campaignPrice = BigDecimal.valueOf(15.99)
            def productInfos = new ArrayList<ProductInfo>()
            def productInfo = new ProductInfo(2L, "test", "test2", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(125))
            productInfos.add(productInfo)
            def basketInfo = new BasketInfo(productInfos)
        when:
            basketInfo.updateGrandTotalWithCampaign("Campaign",  campaignPrice)

        then:
            basketInfo.getGrandTotal() == basketInfo.getSubTotal().add(campaignPrice)
    }

    def "Should update grand total with added new campaign for multiple campaign"(){
        setup:
            def campaign1Price = BigDecimal.valueOf(15.99)
            def campaign2Price = BigDecimal.valueOf(4.33)
            def productInfos = new ArrayList<ProductInfo>()
            def productInfo = new ProductInfo(2L, "test", "test2", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(125))
            productInfos.add(productInfo)
            def basketInfo = new BasketInfo(productInfos)
            basketInfo.updateGrandTotalWithCampaign("Campaign1",  campaign1Price)
        when:
            basketInfo.updateGrandTotalWithCampaign("Campaign2",  campaign2Price)
        then:
            basketInfo.getGrandTotal() == basketInfo.getSubTotal().add(campaign1Price).add(campaign2Price)
    }

    def "Should clear all campaigns from list in basket info"(){
        setup:
        def productInfos = new ArrayList<ProductInfo>()
        def productInfo = new ProductInfo(2L, "test", "test2", 2, BigDecimal.valueOf(100), BigDecimal.valueOf(125))
        productInfos.add(productInfo)
        def basketInfo = new BasketInfo(productInfos)
        basketInfo.updateGrandTotalWithCampaign("Campaign", BigDecimal.valueOf(15.99) )
        when:
        basketInfo.clearCampaigns()
        then:
        basketInfo.getCampaigns().size() == 0
    }

    def "Sub total should be calculated by products"(){
        setup:
            def productInfos = new ArrayList<ProductInfo>()
            def productInfo1Price = BigDecimal.valueOf(60)
            def productInfo2Price = BigDecimal.valueOf(230)
            def productInfo1Quantity = 1
            def productInfo2Quantity = 7
            def productInfo1 = new ProductInfo(2L, "test", "test2", productInfo1Quantity, productInfo1Price, BigDecimal.valueOf(125))
            def productInfo2 = new ProductInfo(1L, "test", "test2", productInfo2Quantity, productInfo2Price, BigDecimal.valueOf(255))
            productInfos.add(productInfo1)
            productInfos.add(productInfo2)
            def basketInfo = new BasketInfo()
        when:
            basketInfo.updateSubTotal(productInfos)
        then:
            basketInfo.getSubTotal() == (productInfo1Price * productInfo1Quantity) + (productInfo2Price * productInfo2Quantity)
    }
}
