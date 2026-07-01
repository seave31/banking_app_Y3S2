package com.example.banking_app_y3s2.models;

import com.google.gson.annotations.SerializedName;

public class SendMoneyRequest {
    @SerializedName("transaction_type")
    private String transactionType;
    @SerializedName("target_account_number")
    private String targetAccountNumber;
    private double amount;
    private String remark;

    public SendMoneyRequest(String transactionType, String targetAccountNumber, double amount, String remark){
        this.transactionType = transactionType;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
        this.remark = remark;
    }
}
