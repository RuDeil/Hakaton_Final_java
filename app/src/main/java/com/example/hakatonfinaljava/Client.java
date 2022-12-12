package com.example.hakatonfinaljava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

public class Client extends AppCompatActivity {
    private String macAddrUser;
    private String macAddrWork = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiManager.getConnectionInfo();
       macAddrUser = wifiInf.getBSSID().toString();
       CheckMAC(macAddrUser);

    }
    public void CheckMAC(String macAddrUser) {
        //if (macAddrUser != null $$ macAddrUser !="02:00:00:00:00:00")
        if (macAddrUser == "02:00:00:00:00:00"){
            //TODO Показать popup, что нужно включить Геолокаци и дать разрешение + запросить разрешение
        }
        while (macAddrUser == macAddrWork){
            //TODO отправлять запрос каждые 6 минут
        }


        }

}