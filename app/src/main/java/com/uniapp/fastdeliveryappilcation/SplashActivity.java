package com.uniapp.fastdeliveryappilcation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private int timeoutMillis = 4000; //time-out of splash activity
    private long startTimeMillis = 0;  //start time of splash
    private boolean sessionLoggedIn  = false;
    private static final String TAG = SplashActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dexterPermissionCheck();
    }

    private void dexterPermissionCheck() {
        Dexter.withActivity(this)
            .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE
            )
            .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        startNextActivity();
                    }
                    else
                        dexterPermissionCheck();

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).
            withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
            .onSameThread()
            .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Permission Denied")
            .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OPEN SETTINGS", (dialog, which) -> {
                dialog.cancel();
                openSettings();
            })
            .show();
    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivityForResult(intent, 101);
    }

    private void startNextActivity() {
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this,LoginActivity.class)),timeoutMillis);
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }
}

