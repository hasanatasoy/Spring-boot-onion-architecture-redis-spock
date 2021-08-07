package com.trendyol.product.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockChangedMessage {
    private long productId;
    private int quantity;
    private int oldQuantity;
}
