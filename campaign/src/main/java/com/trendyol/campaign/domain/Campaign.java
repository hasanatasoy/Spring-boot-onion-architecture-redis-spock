package com.trendyol.campaign.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Campaign {
    @Id
    private long id;
    private String name;
    private BigDecimal toBeAppliedPrice;
    private BigDecimal threshold;
    private BigDecimal actualPrice;
}
