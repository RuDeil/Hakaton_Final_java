package com.example.hakatonfinaljava.client;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hakatonfinaljava.R;

import java.util.ArrayList;

import io.reactivex.rxjava3.annotations.NonNull;

public class ClientActivity extends AppCompatActivity {
    private String macAddrUser;


    ArrayList<String> MacArray;
    TextView tvUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initToolbar();
        checkPermission();
        tvUserName = findViewById(R.id.TvUser);
        macAddrUser = getMacAddrUser();

        int resultChekMac = 0;
        resultChekMac = CheckMac(MacArray, getMacAddrUser());
        if (resultChekMac!=0){
            
        }


    }



    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {



            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }
        }
    }



    public int CheckMac(ArrayList<String> MacArray, String macAddrUser){
        int alarm = 0;
        for(int i =0;MacArray.size()>i;i++){
            if (getMacAddrUser() == MacArray.get(i)){
                alarm++;
            }
        }
        return alarm;

    }
    public String getMacAddrUser() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiManager.getConnectionInfo();
        return wifiInf.getBSSID().toString();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


        } else {
            checkPermission();
        }
    }


    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

