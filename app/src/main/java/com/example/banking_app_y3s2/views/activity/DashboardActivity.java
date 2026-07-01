package com.example.banking_app_y3s2.views.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.banking_app_y3s2.views.fragment.AccountFragment;
import com.example.banking_app_y3s2.views.fragment.HistoryFragment;
import com.example.banking_app_y3s2.views.fragment.HomeFragment;
import com.example.banking_app_y3s2.utils.LocaleHelper;
import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.views.fragment.SendFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboardRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
            bottomNavigationView.setPadding(
                    bottomNavigationView.getPaddingLeft(),
                    bottomNavigationView.getPaddingTop(),
                    bottomNavigationView.getPaddingRight(),
                    systemBars.bottom
            );
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                return true;
            }
            if (itemId == R.id.nav_send) {
                replaceFragment(new SendFragment());
                return true;
            }
            if (itemId == R.id.nav_history) {
                replaceFragment(new HistoryFragment());
                return true;
            }
            if (itemId == R.id.nav_account) {
                replaceFragment(new AccountFragment());
                return true;
            }
            return false;
        });

        //after scanning
        String openFragment = getIntent().getStringExtra("open_fragment"); //holds value "send"
        if ("send".equals(openFragment)) {

            //change the bottom tab
            bottomNav.setSelectedItemId(R.id.nav_send);

            SendFragment sendFragment = new SendFragment();
            Bundle bundle = new Bundle();
            bundle.putString("account_number", getIntent().getStringExtra("account_number"));
            bundle.putString("username", getIntent().getStringExtra("username"));
            sendFragment.setArguments(bundle); //pass data to send fragment

            //removes current fragment and puts SendFragment in the container(change fragment)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.dashboardContainer, sendFragment)
                    .commit();
        }
    }

//    private void openSendFragment() {
//
//    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dashboardContainer, fragment)
                .commit();
    }

    //language
//    This method runs before onCreate()
    @Override
    protected void attachBaseContext(Context base) {
        //pass the new localized Context to Android
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }
}
