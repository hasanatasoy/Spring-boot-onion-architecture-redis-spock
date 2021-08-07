package com.trendyol.basket.externalservice.customer;

import com.trendyol.basket.externalservice.customer.request.GetCustomerRequest;
import com.trendyol.basket.externalservice.customer.response.GetCustomerResponse;

public interface CustomerService {
    GetCustomerResponse get(GetCustomerRequest getCustomerRequest);
}
