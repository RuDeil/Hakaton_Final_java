package com.example.hakatonfinaljava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;

public class Client extends AppCompatActivity {
    private String macAddrUser;
    private String macAddrWork = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initToolbar();
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiManager.getConnectionInfo();
       macAddrUser = wifiInf.getBSSID().toString();
       //CheckMAC(macAddrUser);

    }
    /*public void CheckMAC(String macAddrUser) {
        //if (macAddrUser != null $$ macAddrUser !="02:00:00:00:00:00")
        if (macAddrUser == "02:00:00:00:00:00"){
            //TODO Показать popup, что нужно включить Геолокаци и дать разрешение + запросить разрешение
        }
        while (macAddrUser == macAddrWork){
            //TODO отправлять запрос каждые 5 минут
        }


        }
*/


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

