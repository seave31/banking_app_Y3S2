package com.example.banking_app_y3s2;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("name")
    private String name;
    private String email;
    private String phone;
    private String password;
    private String firstname;
    private String lastname;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("phone_number")
    private String phoneNumber;

    private String address;

    @SerializedName("account_type")
    private String accountType;

    @SerializedName("currency_id")
    private int currencyId;

    @SerializedName("account_status")
    private String accountStatus;
    public RegisterRequest(
            String name,
            String email,
            String phone,
            String password,
            String firstname,
            String lastname,
            String dateOfBirth,
            String phoneNumber,
            String address,
            String accountType,
            int currencyId,
            String accountStatus
    ) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accountType = accountType;
        this.currencyId = currencyId;
        this.accountStatus = accountStatus;
    }

}
