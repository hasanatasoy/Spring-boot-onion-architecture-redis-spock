package com.trendyol.product.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdatePriceRequest {
    private long productId;
    private BigDecimal price;
}
