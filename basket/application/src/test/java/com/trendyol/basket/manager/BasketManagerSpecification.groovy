package com.trendyol.basket.manager

import com.trendyol.basket.converter.impl.BasketDtoConverter
import com.trendyol.basket.converter.impl.BasketInfoDtoConverter
import com.trendyol.basket.converter.impl.CampaignDtoConverter
import com.trendyol.basket.converter.impl.CampaignProductInfoDtoConverter
import com.trendyol.basket.converter.impl.ProductInfoDtoConverter
import com.trendyol.basket.entity.Basket
import com.trendyol.basket.entity.ProductInfo
import com.trendyol.basket.externalservice.campaign.CampaignService
import com.trendyol.basket.externalservice.campaign.model.dto.CampaignProductInfoDTO
import com.trendyol.basket.externalservice.campaign.model.request.GetCampaignRequest
import com.trendyol.basket.externalservice.campaign.model.response.GetCampaignResponse
import com.trendyol.basket.externalservice.product.ProductService
import com.trendyol.basket.externalservice.product.model.request.GetProductRequest
import com.trendyol.basket.externalservice.product.model.response.GetProductResponse
import com.trendyol.basket.manager.impl.BasketManagerImpl
import com.trendyol.basket.model.dto.CampaignDTO
import com.trendyol.basket.model.dto.ProductInfoDTO
import com.trendyol.basket.model.request.AddToBasketRequest
import com.trendyol.basket.model.request.GetBasketRequest
import com.trendyol.basket.model.request.UpdateBasketRequest
import com.trendyol.basket.model.response.AddToBasketResponse
import com.trendyol.basket.repository.BasketRepository
import com.trendyol.basket.services.BasketService
import com.trendyol.basket.validator.impl.AddToBasketRequestValidator
import com.trendyol.basket.validator.impl.GetBasketRequestValidator
import com.trendyol.basket.validator.impl.UpdateBasketRequestValidator
import spock.lang.Shared
import spock.lang.Specification
import java.util.stream.Collectors

class BasketManagerSpecification extends Specification{

    def price = BigDecimal.valueOf(100)
    def oldPrice = BigDecimal.valueOf(150)
    def customerId = 1L
    def productId = 2L
    def imageUrl = "TestImageUrl"
    def title = "TestTitle"
    def quantity = 1
    @Shared AddToBasketRequestValidator addToBasketRequestValidator
    @Shared UpdateBasketRequestValidator updateBasketRequestValidator
    @Shared GetBasketRequestValidator getBasketRequestValidator
    @Shared BasketDtoConverter basketDtoConverter
    @Shared CampaignProductInfoDtoConverter campaignProductInfoDtoConverter

    def setupSpec(){
        addToBasketRequestValidator = new AddToBasketRequestValidator()
        updateBasketRequestValidator = new UpdateBasketRequestValidator()
        getBasketRequestValidator = new GetBasketRequestValidator()
        basketDtoConverter = new BasketDtoConverter(new BasketInfoDtoConverter(new CampaignDtoConverter()), new ProductInfoDtoConverter())
        campaignProductInfoDtoConverter = new CampaignProductInfoDtoConverter()
    }

    def "When item added to basket then no exception thrown"(){
        setup:
            def addToBasketRequest = new AddToBasketRequest()
            addToBasketRequest.setCustomerId(customerId)
            addToBasketRequest.setProductId(productId)
            addToBasketRequest.setQuantity(quantity)
            def getProductResponse = new GetProductResponse(
                    new ProductInfoDTO(
                            productId,
                            imageUrl,
                            title,
                            quantity,
                            price,
                            oldPrice
                    )
            )
            def getProductResponseDTO = getProductResponse.getProductInfoDto()
            def basket = new Basket(addToBasketRequest.getCustomerId(),
                    new ProductInfo(
                            getProductResponseDTO.getId(),
                            getProductResponseDTO.getImageUrl(),
                            getProductResponseDTO.getTitle(),
                            getProductResponseDTO.getQuantity(),
                            getProductResponseDTO.getPrice(),
                            getProductResponseDTO.getOldPrice()
                    ))

            def productService = Mock(ProductService){
                get(_ as GetProductRequest) >> getProductResponse
            }
            def basketService = Mock(BasketService) {
                add(
                        addToBasketRequest.getCustomerId(),
                        getProductResponseDTO.getId(),
                        getProductResponseDTO.getImageUrl(),
                        getProductResponseDTO.getTitle(),
                        getProductResponseDTO.getQuantity(),
                        getProductResponseDTO.getPrice(),
                        getProductResponseDTO.getOldPrice()

                ) >> basket
            }
            def campaignService = Mock(CampaignService){
                def campaignDTOs = new ArrayList<CampaignDTO>()
                campaignDTOs.add(new CampaignDTO("Campaign", BigDecimal.valueOf(4.99)))
                def getCampaignResponse = new GetCampaignResponse(basket.getCustomerId(), campaignDTOs)
                get(_ as GetCampaignRequest) >> getCampaignResponse
            }

            def basketManager = new BasketManagerImpl(
                    basketService,
                    productService,
                    campaignService,
                    updateBasketRequestValidator,
                    addToBasketRequestValidator,
                    getBasketRequestValidator,
                    basketDtoConverter,
                    campaignProductInfoDtoConverter)
        when:
            basketManager.add(addToBasketRequest)
        then:
            noExceptionThrown()
    }

    def "When item updated then no exception thrown"(){
        setup:
        def updateBasketRequest = new UpdateBasketRequest()
        updateBasketRequest.setCustomerId(customerId)
        updateBasketRequest.setProductId(productId)
        updateBasketRequest.setQuantity(quantity)
        def getProductResponse = new GetProductResponse(
                new ProductInfoDTO(
                        productId,
                        imageUrl,
                        title,
                        quantity,
                        price,
                        oldPrice
                )
        )
        def getProductResponseDTO = getProductResponse.getProductInfoDto()
        def basket = new Basket(updateBasketRequest.getCustomerId(),
                new ProductInfo(
                        getProductResponseDTO.getId(),
                        getProductResponseDTO.getImageUrl(),
                        getProductResponseDTO.getTitle(),
                        getProductResponseDTO.getQuantity(),
                        getProductResponseDTO.getPrice(),
                        getProductResponseDTO.getOldPrice()
                ))

        def productService = Mock(ProductService){
            get(_ as GetProductRequest) >> getProductResponse
        }
        def basketService = Mock(BasketService) {
            update(
                    updateBasketRequest.getCustomerId(),
                    getProductResponseDTO.getId(),
                    getProductResponseDTO.getQuantity()
            ) >> basket
        }
        def campaignService = Mock(CampaignService){
            def campaignDTOs = new ArrayList<CampaignDTO>()
            campaignDTOs.add(new CampaignDTO("Campaign", BigDecimal.valueOf(4.99)))
            def getCampaignResponse = new GetCampaignResponse(basket.getCustomerId(), campaignDTOs)
            get(_ as GetCampaignRequest) >> getCampaignResponse
        }

        def basketManager = new BasketManagerImpl(
                basketService,
                productService,
                campaignService,
                updateBasketRequestValidator,
                addToBasketRequestValidator,
                getBasketRequestValidator,
                basketDtoConverter,
                campaignProductInfoDtoConverter)
        when:
            basketManager.update(updateBasketRequest)
        then:
            noExceptionThrown()
    }

    def "When item get by customer id then no exception thrown"(){
        setup:
        def getBasketRequest = new GetBasketRequest()
        getBasketRequest.setCustomerId(customerId)
        def basket = new Basket(customerId,
                new ProductInfo(
                        productId,
                        imageUrl,
                        title,
                        quantity,
                        price,
                        oldPrice
                )
        )
        def basketService = Mock(BasketService) {
            get(getBasketRequest.getCustomerId()) >> basket
        }
        def basketManager = new BasketManagerImpl(
                basketService,
                null,
                null,
                updateBasketRequestValidator,
                addToBasketRequestValidator,
                getBasketRequestValidator,
                basketDtoConverter,
                campaignProductInfoDtoConverter)
        when:
            basketManager.get(getBasketRequest)
        then:
            noExceptionThrown()
    }
}
