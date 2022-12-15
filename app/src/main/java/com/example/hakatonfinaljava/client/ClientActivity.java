package com.example.hakatonfinaljava.client;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.models.ClientData;
import com.example.hakatonfinaljava.models.OnlineData;
import com.example.hakatonfinaljava.net.NetModule;
import com.example.hakatonfinaljava.responses.BaseResponse;
import com.example.hakatonfinaljava.responses.LoginResponse;
import com.example.hakatonfinaljava.responses.MAC;
import com.example.hakatonfinaljava.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientActivity extends AppCompatActivity {
    ArrayList<MAC> MacArray = new ArrayList<MAC>();
    TextView tvUserName;
    AppCompatButton btnExit, btnUpdate;
    private LoginResponse loginResponse = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        checkPermission();

        tvUserName = findViewById(R.id.TvUser);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnExit = findViewById(R.id.btnExit);

        loginResponse = (LoginResponse) getIntent().getSerializableExtra("KeyLoginResponse");
        String USER_ID = loginResponse.getUserID();
        initView(loginResponse.getUsername(), loginResponse.getUser());
        initToolbar();
        MacArray = loginResponse.getMacs();



        btnUpdate.setOnClickListener(view -> setOnlineRequest(loginResponse.getUserID(), loginResponse.getToken()));

        btnExit.setOnClickListener(view -> setLogoutRequest(loginResponse.getToken()));



        Observable.just(true).repeatWhen(t->t.delay(10,TimeUnit.SECONDS)).subscribe(b->{setOnlineRequest(USER_ID , loginResponse.getToken());});



    }







    private void setLogoutRequest(String token) {
        String header = "bearer "+ token;
        NetModule netModule = new NetModule();
        netModule.getNetService().logout(header)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::closeApp)
                .subscribe();
    }

    private void closeApp() {
        finishAndRemoveTask();
    }

    private void setOnlineRequest(String userID, String token) {
        OnlineData onlineData = new OnlineData(userID, getMacAddrUser());
        NetModule netModule = new NetModule();
        String header = token;
        netModule.getNetService().online(header, onlineData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::handleResponse)
                .doOnError(this::handleError)
                .subscribe();


    }

    private void handleError(Throwable error) {
        Log.e("TAG", "handleError: " + error.getMessage());
        Utils.handleError(error, this);
    }

    private void handleResponse(Response<BaseResponse> baseResponseResponse) {
        try {
            BaseResponse response = Utils.handleResults(baseResponseResponse);
            if (response.getResult() == 4) {
                // todo show error if result is bad
            } else {
                // todo all is ok
            }
        } catch (Throwable e) {
            handleError(e);
        }
    }

    private void initView(String username, Integer user) {
        tvUserName.setText(username);
        switch (user){
            case 0: {
                btnExit.setVisibility(View.GONE);
            }
                break;
            case 1: {
                btnExit.setVisibility(View.VISIBLE);
            }
            break;
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





    public String getMacAddrUser() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
        setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}

