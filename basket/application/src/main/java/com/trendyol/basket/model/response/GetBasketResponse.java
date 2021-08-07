package com.trendyol.basket.model.response;

import com.trendyol.basket.model.dto.BasketDTO;

public class GetBasketResponse {
    private BasketDTO basketDTO;

    public GetBasketResponse(BasketDTO basketDTO) {
        this.basketDTO = basketDTO;
    }

    public BasketDTO getBasketDTO() {
        return basketDTO;
    }

    public void setBasketDTO(BasketDTO basketDTO) {
        this.basketDTO = basketDTO;
    }
}
