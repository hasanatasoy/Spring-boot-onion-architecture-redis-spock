package com.trendyol.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateStockRequest {
    private long productId;
    private int quantity;
}
