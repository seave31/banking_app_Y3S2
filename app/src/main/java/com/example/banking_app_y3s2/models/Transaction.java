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
    @SerializedName("direction")
    String direction;
    @SerializedName("sender")
    String sender;
    @SerializedName("sender_acc")
    String senderAcc;
    @SerializedName("receiver")
    String receiver;


    //getters
    public String getTransactionType() {
        return transactionType;
    }
    public double getAmount(){
        return amount;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
    public String getRemark(){
        return remark;
    }
    public String getCreatedAt(){
        return createdAt;
    }

    public String getDirection() {
        return direction;
    }
    public String getSender() {
        return sender;
    }
    public String getSenderAcc(){
        return senderAcc;
    }
    public String getReceiver(){
        return receiver;
    }
}
