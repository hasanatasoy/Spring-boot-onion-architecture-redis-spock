package com.trendyol.customer;

import com.trendyol.basket.externalservice.customer.CustomerService;
import com.trendyol.basket.externalservice.customer.request.GetCustomerRequest;
import com.trendyol.basket.externalservice.customer.response.GetCustomerResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public GetCustomerResponse get(GetCustomerRequest getCustomerRequest) {
        return new GetCustomerResponse(
                getCustomerRequest.getCustomerId(),
                "atasoyhasan96@gmail.com",
                "Hasan",
                "Atasoy"
        );
    }
}
