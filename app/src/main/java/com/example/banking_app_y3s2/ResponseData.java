package com.example.banking_app_y3s2;

import com.example.banking_app_y3s2.models.Account;
import com.example.banking_app_y3s2.models.Customer;
import com.example.banking_app_y3s2.models.User;
import com.google.gson.annotations.SerializedName;

public class ResponseData {
    private String status;
    private User user;
    private Customer customer;
    private Account account;
    @SerializedName("token")
    private String token;

    public String getStatus() {
        return status;
    }
    public User getUser() {
        return user;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Account getAccount() {
        return account;
    }

    public String getToken() {
        return token;
    }
}
