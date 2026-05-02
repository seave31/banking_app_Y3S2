package com.example.banking_app_y3s2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banking_app_y3s2.databinding.ActivityMainBinding;
import com.example.banking_app_y3s2.databinding.ActivityScanQrBinding;
import com.example.banking_app_y3s2.views.GetResultActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQrActivity extends AppCompatActivity {
    ActivityScanQrBinding binding;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        //INIT QR SCANNER HERE
        barcodeLauncher = registerForActivityResult(
                new ScanContract(),
                result -> {
                    if(result.getContents() != null) {
                        //result from qr is string
                        try {
                            handleScanResult(result.getContents());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        startScanner();
    }

    private void startScanner() {
        ScanOptions options = new ScanOptions();
//        options.setPrompt("Scan QR Code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);

        barcodeLauncher.launch(options);
    }

    private void handleScanResult(String qrData) throws JSONException {
        JSONObject object = new JSONObject(qrData);
        String accNum = object.getString("account_number");
        String username = object.getString("username");
        Log.i("", "handleScanResult: " + accNum);

        Intent intent = new Intent(ScanQrActivity.this, DashboardActivity.class);
        intent.putExtra("account_number", accNum);
        intent.putExtra("username", username);
        intent.putExtra("open_fragment", "send");

        startActivity(intent);
        finish();


    }
}
