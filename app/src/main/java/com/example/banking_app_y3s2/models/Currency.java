package com.example.banking_app_y3s2.models;

public class Currency {
    private int id;
    private String code;
    private String symbol;
    private String default_balance;
    private String rate_to_usd;
    private String created_at;
    private String updated_at;

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getSymbol() { return symbol; }
    public String getDefaultBalance() { return default_balance; }
    public String getRateToUsd() { return rate_to_usd; }
    public String getCreatedAt() { return created_at; }
    public String getUpdatedAt() { return updated_at; }
}
