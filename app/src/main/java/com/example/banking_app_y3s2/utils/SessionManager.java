package com.example.banking_app_y3s2.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {
    private SharedPreferences preferences;

    public SessionManager(Context context){
        preferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    public void saveUser(String token, String name, String account){
        preferences.edit()
                .putString("token", token)
                .putString("name", name)
                .putString("acc_number", account)
                .apply();
    }
    public String getName() {
        return preferences.getString("name", "");
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public String getAccount() {
        return preferences.getString("acc_number", "");
    }
    public void logout(){
        preferences.edit().clear().apply();
    }

}
