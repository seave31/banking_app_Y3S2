package com.example.banking_app_y3s2;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest(String phone, String password){
        this.email = phone;
        this.password = password;
    }
}
