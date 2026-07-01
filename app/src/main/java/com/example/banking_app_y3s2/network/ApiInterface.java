package com.example.banking_app_y3s2.network;

import com.example.banking_app_y3s2.models.Account;
import com.example.banking_app_y3s2.models.LoginRequest;
import com.example.banking_app_y3s2.models.LogoutResponse;
import com.example.banking_app_y3s2.models.RegisterRequest;
import com.example.banking_app_y3s2.models.ResponseData;
import com.example.banking_app_y3s2.models.SendMoneyRequest;
import com.example.banking_app_y3s2.models.Transaction;
import com.example.banking_app_y3s2.models.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("currencies")
    Call<List<ResponseData>> getCurrencies();

    @POST("login")
    Call<ResponseData> loginUser(@Body LoginRequest loginRequest);

    @POST("logout")
    Call<LogoutResponse> logoutUser(@Header("Authorization") String token);

    @POST("signup")
    Call<ResponseData> registerUser(@Body RegisterRequest registerRequest);


    @POST("user")
    Call<ResponseData> getUser(@Header("Authorization") String token);

    @POST("transfer")
    Call<TransactionResponse> sendMoney(@Header("Authorization") String token, @Body SendMoneyRequest request);

    @GET("transactions")
    Call<List<Transaction>> getTransactionHistory(@Header("Authorization") String token);

    @GET("user/accNum/{accNo}")
    Call<Account> getUserByAccNum(
            @Path("accNo") String accountNumber
    );
}

