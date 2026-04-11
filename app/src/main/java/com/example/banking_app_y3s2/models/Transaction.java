package com.example.banking_app_y3s2.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("transaction_type")
    String transactionType;
    @SerializedName("amount")
    double amount;
    @SerializedName("ref_code")
    String refCode;
    @SerializedName("target_account_number")
    String targetAccountNumber;
    @SerializedName("remark")
    String remark;
    @SerializedName("created_at")
    String createdAt;
}
