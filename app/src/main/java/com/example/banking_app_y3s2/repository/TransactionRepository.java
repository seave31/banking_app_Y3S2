package com.example.banking_app_y3s2.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.banking_app_y3s2.network.RetrofitInstance;
import com.example.banking_app_y3s2.models.SendMoneyRequest;
import com.example.banking_app_y3s2.models.Transaction;
import com.example.banking_app_y3s2.models.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRepository {

    //send money
    public LiveData<TransactionResponse> sendMoney(String token, String targetAccountNumber, double amount, String remark){
        MutableLiveData<TransactionResponse> data = new MutableLiveData<>();
        SendMoneyRequest request = new SendMoneyRequest("Transfer", targetAccountNumber, amount, remark);

        RetrofitInstance.getApiInterface().sendMoney(token, request).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    data.setValue(response.body());
                    Log.d("API_RESPONSE", new com.google.gson.Gson().toJson(response.body()));
                }else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    //transaction history
    public LiveData<List<Transaction>> getTransactionHistory(String token){
        MutableLiveData<List<Transaction>> data = new MutableLiveData<>();
        RetrofitInstance.getApiInterface().getTransactionHistory(token).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if(response.isSuccessful() && response.body() != null){
                    data.setValue(response.body());
                    Log.d("API_RESPONSE", new com.google.gson.Gson().toJson(response.body()));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
