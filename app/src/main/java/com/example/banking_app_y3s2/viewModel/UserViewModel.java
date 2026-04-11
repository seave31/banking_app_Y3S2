package com.example.banking_app_y3s2.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.banking_app_y3s2.ResponseData;
import com.example.banking_app_y3s2.repository.UserRepository;


public class UserViewModel extends ViewModel {
    private UserRepository repository = new UserRepository();
    public LiveData<ResponseData> getCurrentUser(String token){
        return repository.getCurrentUser(token);
    }


}
