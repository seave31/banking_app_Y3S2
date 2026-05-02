package com.example.banking_app_y3s2.models;

import com.google.gson.annotations.SerializedName;

public class TransactionResponse {
    private String status;
    @SerializedName("transaction")
    private Transaction transaction;
    @SerializedName("person_a")
    private String personA;
    @SerializedName("person_b")
    private String personB;

    public String getStatus(){
        return status;
    }

    public Transaction getTransaction(){
        return transaction;
    }
    public String getPersonA(){
        return personA;
    }
    public String getPersonB(){
        return personB;
    }
}
