package com.example.hakatonfinaljava.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.models.LoginData;
import com.example.hakatonfinaljava.net.NetModule;
import com.example.hakatonfinaljava.registration.RegistrationActivity;
import com.example.hakatonfinaljava.boss.BossActivity;
import com.example.hakatonfinaljava.client.ClientActivity;
import com.example.hakatonfinaljava.responses.LoginResponse;
import com.example.hakatonfinaljava.responses.MAC;
import com.example.hakatonfinaljava.utils.Utils;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;

public class MainActivity extends AppCompatActivity {




    TextInputLayout textInputLayoutPhone, textInputLayoutPass, textInputLayoutGuest;
    private TextInputEditText etLoginNumber, etLoginPass, etGuest;
    private Button btnLogin;
    private Disposable single;
    private Button btnRegister;
    private Boolean isBoss = true;
    private Integer userType = 0; //  (0 - гость, 1 - сотрудник, 2 - начальник)



    private String error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Инпуты
        etLoginNumber = findViewById(R.id.ETLoginNumber);
        etLoginPass = findViewById(R.id.ETLoginPass);
        etGuest = findViewById(R.id.etGuest);
        //Кнопки
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnReg);

        //Слушатели кнопок
        btnLogin.setOnClickListener(this::onClick);
        btnRegister.setOnClickListener(this::onClick);
        //Layout

        textInputLayoutPhone = findViewById(R.id.textInputLayoutNumber);
        textInputLayoutPass = findViewById(R.id.textInputLayoutPass);
        textInputLayoutGuest = findViewById(R.id.textInputLayoutGuest);

        //свитч
        SwitchMaterial swGuest;
        swGuest = findViewById(R.id.switchLogin);
        swGuest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnRegister.setVisibility(View.INVISIBLE);
                    etLoginNumber.setVisibility(View.INVISIBLE);
                    etLoginPass.setVisibility(View.INVISIBLE);
                    textInputLayoutPass.setVisibility(View.INVISIBLE);
                    textInputLayoutPhone.setVisibility(View.INVISIBLE);
                    textInputLayoutGuest.setVisibility(View.VISIBLE);
                    etGuest.setVisibility(View.VISIBLE);
                    userType = 0;


                } else {
                    btnRegister.setVisibility(View.VISIBLE);
                    etLoginNumber.setVisibility(View.VISIBLE);
                    etLoginPass.setVisibility(View.VISIBLE);
                    textInputLayoutPass.setVisibility(View.VISIBLE);
                    textInputLayoutPhone.setVisibility(View.VISIBLE);
                    textInputLayoutGuest.setVisibility(View.INVISIBLE);
                    etGuest.setVisibility(View.INVISIBLE);
                    userType = 1;
                }
            }
        });

        etLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() != 12) {
                    textInputLayoutPhone.setErrorEnabled(true);
                    error = "Некорректный номер телефона";
                    textInputLayoutPhone.setError(error);


                } else {
                    textInputLayoutPhone.setErrorEnabled(false);
                    error = "";
                    textInputLayoutPhone.setError(error);
                }
            }


        });


    }


    protected void onClick(View v) {

        {
            Intent intent;
            switch (v.getId()) {

                case R.id.btnLogin: {

                    String numberUser = etLoginNumber.getText().toString();
                    String passUser = etLoginPass.getText().toString();

                    NetModule netModule = new NetModule();
                    LoginData loginData = new LoginData(numberUser, passUser, userType);
                    startLoading();

                    netModule.getNetService().login(loginData).
                            subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(this::finishLoading)
                            .doOnSuccess(this::handleResponse)
                            .doOnError(error -> Utils.handleError(error, this))
                            .subscribe();


                }
                case R.id.btnReg:{
                    intent = new Intent(MainActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
    private void handleResponse(Response<LoginResponse> result) {
        try {




        } catch (Throwable e) {
            Utils.handleError(e, this);
            textInputLayoutPhone.setErrorEnabled(true);


        }
    }
    private void finishLoading() {


    }

    private void openAuthScreen(LoginResponse loginResponse) {
        switch (loginResponse.getUser()) {
            //Если юзер гость - переходит на экран обычного юзера - без возможности разлогинится, не убив приложение.
            case 0:
              Intent intent = new Intent(MainActivity.this, ClientActivity.class);
                intent.putExtra("KeyLoginResponse", (Serializable) loginResponse);
                startActivity(intent);

                break;
            //Если юзер Сотрудник - переходит на экран обычного юзера
            case 1:
                intent = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(intent);
                break;


            //Если юзер начальник переходит на активити начальника
            case 2:
                intent = new Intent(MainActivity.this, BossActivity.class);
                startActivity(intent);
                break;
            default:
                textInputLayoutPhone.setErrorEnabled(true);


                break;

        }
    }





    private void startLoading() {
    }


}
