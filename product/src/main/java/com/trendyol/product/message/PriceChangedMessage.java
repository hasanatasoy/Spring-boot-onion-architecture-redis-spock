package com.trendyol.product.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class PriceChangedMessage {
    private long productId;
    private BigDecimal price;
    private BigDecimal oldPrice;
}
