package com.example.banking_app_y3s2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleHelper {
    public static Context setLocale(Context context, String langCode) {
        saveLanguage(context, langCode);

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        return context.createConfigurationContext(config);
    }

    public static void saveLanguage(Context context, String langCode) {
        SharedPreferences prefs = context.getSharedPreferences("LANG", Context.MODE_PRIVATE);
        prefs.edit().putString("lang", langCode).apply();
    }

    public static String loadLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("LANG", Context.MODE_PRIVATE);
        return prefs.getString("lang", "en"); // Default English
    }
}
