package com.trendyol.basket.externalservice.customer.response;

public class GetCustomerResponse {
    private long customerId;
    private String email;
    private String firstname;
    private String lastname;

    public GetCustomerResponse(long customerId, String email, String firstname, String lastname) {
        this.customerId = customerId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
