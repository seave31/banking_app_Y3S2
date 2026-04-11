package com.example.banking_app_y3s2.models;

public class Customer {
    private int user_id;
    private String firstname;
    private String lastname;
    private String date_of_birth;
    private String phone_number;
    private String address;
    private String updated_at;
    private String created_at;
    private int id;

    public int getUserId() { return user_id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getDateOfBirth() { return date_of_birth; }
    public String getPhoneNumber() { return phone_number; }
    public String getAddress() { return address; }
    public String getUpdatedAt() { return updated_at; }
    public String getCreatedAt() { return created_at; }
    public int getId() { return id; }
}

