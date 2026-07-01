package com.example.banking_app_y3s2.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.banking_app_y3s2.models.LoginRequest;
import com.example.banking_app_y3s2.models.RegisterRequest;
import com.example.banking_app_y3s2.models.ResponseData;
import com.example.banking_app_y3s2.network.RetrofitInstance;
import com.example.banking_app_y3s2.models.Account;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public LiveData<ResponseData> login(String phone, String pin){
        MutableLiveData<ResponseData> data = new MutableLiveData<>();

        //post body
        LoginRequest request = new LoginRequest(phone, pin);
        RetrofitInstance.getApiInterface().loginUser(request).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("FULL_JSON", new Gson().toJson(response.body()));
                    data.setValue(response.body());
                } else {

                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<ResponseData> register(String name, String email, String phone, String password, String firstname, String lastname, String dob, String phoneNumber, String address, String accountType, int currencyId, String accountStatus){
        MutableLiveData<ResponseData> data = new MutableLiveData<>();

        //public RegisterRequest(String fullname, String email, String phone, String password, String firstname, String lastname, String date_of_birth, String address, String account_type, int currency_id, String account_status){
        //post body
        RegisterRequest request = new RegisterRequest(name, email, phone, password, firstname, lastname, dob, phoneNumber, address, accountType, currencyId, accountStatus);
        RetrofitInstance.getApiInterface().registerUser(request).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("REGISTER", "HTTP Code: " + response.code());
                Log.d("REGISTER", "Successful: " + response.isSuccessful());

                if (response.body() != null) {
                    Log.d("REGISTER", "Body: " + new Gson().toJson(response.body()));
                }

                if (response.errorBody() != null) {
                    try {
                        Log.e("REGISTER", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e("REGISTER", "Failure", t);
                data.setValue(null);
            }

        });
        return data;
    }

    public LiveData<ResponseData> getCurrentUser(String token){
        MutableLiveData<ResponseData> data = new MutableLiveData<>();

        RetrofitInstance.getApiInterface().getUser(token).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("API_RESPONSE", new com.google.gson.Gson().toJson(response.body()));
                    data.setValue(response.body());
                }else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Account> getUserByAccNum(String accountNumber){
        MutableLiveData<Account> data = new MutableLiveData<>();
        RetrofitInstance.getApiInterface().getUserByAccNum(accountNumber).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("user by acc no : API_RESPONSE", new com.google.gson.Gson().toJson(response.body()));
                    data.setValue(response.body());
                }else {
                    Account account = new Account();
                    account.setMessage("Account not found");
                    data.setValue(account);

                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }




}
