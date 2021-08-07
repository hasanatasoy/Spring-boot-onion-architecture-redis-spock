package com.trendyol.product.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String imageUrl;
    private String title;
    private int quantity;
    private BigDecimal price;
    private BigDecimal oldPrice;
}
