package com.example.banking_app_y3s2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.databinding.ActivityMainBinding;
import com.example.banking_app_y3s2.models.Account;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.AuthViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //create acc button
        binding.createAccountText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        //assign viewModel
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        //sign in button
        binding.signInButton.setOnClickListener(v -> {
            //get user input
            String phone = binding.phoneInput.getText().toString();
            String pin = binding.pinInput.getText().toString();


            viewModel.login(phone, pin).observe(this, data->{

                if(data != null){
                    //token
                    String token = data.getToken();
                    Log.d("token", token);

                    //name
                    String name = data.getUser().getName();

                    //account number
                    String accountNumber = "";
                    if(data.getAccount() != null){
                        accountNumber = data.getAccount().getAccountNumber();
                    }

                    SessionManager sessionManager = new SessionManager(this);
                    sessionManager.saveUser(token, name, accountNumber);

                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });


        });
    }

//    private void loginUser(String phone, String pin){
//        ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setMessage("Loading...");
//        dialog.show();
//
//        LoginRequest request = new LoginRequest(phone, pin);
//        RetrofitInstance.getApiInterface().loginUser(request).enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                dialog.dismiss();
//                if(response.isSuccessful() && response.body() != null){
//                    String token = response.body().getToken();
//                    String name = response.body().getUser().getName();
//
//                    String account = response.body().getAccount().getAccountNumber();
//                    if(account == null){
//                        Toast.makeText(MainActivity.this, "Cannot get account number", Toast.LENGTH_SHORT).show();
//                    }
//
//                    //sharedPreferences
//                    SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("token", token);
//                    editor.putString("name", name);
//                    editor.putString("acc_number", account);
//                    editor.apply();
//
//
//                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this,
//                        t.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void attachBaseContext(Context base) {
        //pass the new localized Context to Android
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }


}