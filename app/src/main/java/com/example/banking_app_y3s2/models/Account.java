package com.example.banking_app_y3s2.models;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("customer_id")
    private int customerId;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("account_type")
    private String accountType;
    @SerializedName("currency_id")
    private int currencyId;
    @SerializedName("account_status")
    private String accountStatus;
    @SerializedName("balance")
    private double balance;
    private String updated_at;
    private String created_at;
    private int id;
    private Currency currency; // nested
    //new added
    @SerializedName("account_name")
    private String accountName;

    public int getCustomerId() { return customerId; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public int getCurrencyId() { return currencyId; }
    public String getAccountStatus() { return accountStatus; }
    public double getBalance() { return balance; }
    public String getUpdatedAt() { return updated_at; }
    public String getCreatedAt() { return created_at; }
    public int getId() { return id; }
    public Currency getCurrency() { return currency; }
    public String getAccountName() { return accountName; }
}