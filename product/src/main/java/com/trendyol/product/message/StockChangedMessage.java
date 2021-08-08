package com.trendyol.product.message;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;

@Getter
@Setter
public class StockChangedMessage{
    private long productId;
    private int quantity;
    private int oldQuantity;
}
