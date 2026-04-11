package com.example.banking_app_y3s2.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.banking_app_y3s2.LoginRequest;
import com.example.banking_app_y3s2.ResponseData;
import com.example.banking_app_y3s2.RetrofitInstance;
import com.google.gson.Gson;

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




}
