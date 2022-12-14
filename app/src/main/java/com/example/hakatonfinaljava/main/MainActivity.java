package com.example.hakatonfinaljava.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.registration.RegistrationActivity;
import com.example.hakatonfinaljava.boss.BossActivity;
import com.example.hakatonfinaljava.client.ClientActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {


    private OkHttpClient client;

    private TextInputEditText etLoginNumber, etLoginPass, etGuest;
    private Button btnLogin;
    private Disposable single;
    private Button btnRegister;
    private Boolean isBoss = false;


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
        TextInputLayout textInputLayoutPhone, textInputLayoutPass, textInputLayoutGuest;
        textInputLayoutPhone = findViewById(R.id.textInputLayoutNumber);
        textInputLayoutPass = findViewById(R.id.textInputLayoutPass);
        textInputLayoutGuest = findViewById(R.id.textInputLayoutGuest);

        //свитч
        Switch swGuest;
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


               } else {
                   btnRegister.setVisibility(View.VISIBLE);
                   etLoginNumber.setVisibility(View.VISIBLE);
                   etLoginPass.setVisibility(View.VISIBLE);
                   textInputLayoutPass.setVisibility(View.VISIBLE);
                   textInputLayoutPhone.setVisibility(View.VISIBLE);
                   textInputLayoutGuest.setVisibility(View.INVISIBLE);
                   etGuest.setVisibility(View.INVISIBLE);
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
                String error;

               if (s.length() != 12) {
                    textInputLayoutPhone.setErrorEnabled(true);
                    error ="Некорректный номер телефона";
                    textInputLayoutPhone.setError(error);


                }
               else {
                   textInputLayoutPhone.setErrorEnabled(false);
                   error = "";
                   textInputLayoutPhone.setError(error);
               }
            }




        });


    }



    private void onClick(View v) {

        {
            Intent intent;
            switch (v.getId()) {

                case R.id.btnLogin:
                    {
                        //getData(); TODO
                        if(isBoss) {
                        intent = new Intent(MainActivity.this, BossActivity.class);
                        startActivity(intent);
                            }
                        else{
                        intent = new Intent(MainActivity.this, ClientActivity.class);
                        startActivity(intent);
                            }
                    }
                    break;

                case R.id.btnReg:
                    intent = new Intent(MainActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                default:
                    break;
            }
        }
    }



}
