package com.trendyol.basket.converter.impl;

import com.trendyol.basket.converter.Converter;
import com.trendyol.basket.entity.Basket;
import com.trendyol.basket.model.dto.BasketDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BasketDtoConverter implements Converter<Basket, BasketDTO> {

    private BasketInfoDtoConverter basketInfoDtoConverter;
    private ProductInfoDtoConverter productInfoDtoConverter;

    public BasketDtoConverter(
            BasketInfoDtoConverter basketInfoDtoConverter,
            ProductInfoDtoConverter productInfoDtoConverter){
        this.basketInfoDtoConverter = basketInfoDtoConverter;
        this.productInfoDtoConverter = productInfoDtoConverter;
    }

    @Override
    public BasketDTO convert(Basket basket) {
        var basketDTO = new BasketDTO();
        var basketInfoDTO = basketInfoDtoConverter.convert(basket.getBasketInfo());
        basketDTO.setBasketInfoDTO(basketInfoDTO);
        var productInfoDTOs = basket.getProducts().stream()
                .map(productInfoDtoConverter::convert)
                .collect(Collectors.toList());
        basketDTO.setProducts(productInfoDTOs);
        basketDTO.setCustomerId(basket.getCustomerId());
        return basketDTO;
    }
}
