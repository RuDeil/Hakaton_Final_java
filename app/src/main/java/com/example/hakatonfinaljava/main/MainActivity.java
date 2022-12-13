package com.example.hakatonfinaljava.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.registration.RegistrationActivity;
import com.example.hakatonfinaljava.boss.BossActivity;
import com.example.hakatonfinaljava.client.ClientActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private OkHttpClient client;

    private TextInputEditText etLoginNumber, etLoginPass;
    private Button btnLogin;
    private Disposable single;
    private Button btnRegister;
    private Boolean isBoss = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Инпуты
        etLoginNumber = findViewById(R.id.ETLoginNumber);
        etLoginPass = findViewById(R.id.ETLoginPass);
        //Кнопки
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnReg);

        //Слушатели кнопок
        btnLogin.setOnClickListener(this::onClick);
        btnRegister.setOnClickListener(this::onClick);
        //Layout
        TextInputLayout textInputLayoutPhone, textInputLayoutPass;
        textInputLayoutPhone = findViewById(R.id.textInputLayoutNumber);
        textInputLayoutPass = findViewById(R.id.textInputLayoutPass);

        etLoginPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 8) {
                    boolean valid = isValidPassword(s.toString());
                    textInputLayoutPass.setErrorEnabled(valid);
                    String error;
                    if (valid){
                        error = "";}
                    else {
                        error = getString(R.string.invalid_login_message);}

                    textInputLayoutPass.setError(error);
                    if (valid) {
                        Toast toastPass = Toast.makeText(getApplicationContext(),
                                R.string.invalid_login_message, Toast.LENGTH_SHORT);
                        toastPass.show();
                    }

                }
            }
        });


    }

    private void onClick(View v) {

        {
            Intent intent;
            switch (v.getId()) {

                case R.id.btnLogin:
                    if (isValidPassword(etLoginPass.getText().toString())){
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
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Проверьте данные", Toast.LENGTH_SHORT);
                        toast.show();
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
    public static boolean isValidPassword(String password) {

        String regex = ("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])");
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return true; // m.matches();
    }
    private boolean isValidPhone(String Phone) {
        final String PHONE_REGEX = "^\\+?(\\d{7})";
        final Pattern pattern = Pattern.compile(PHONE_REGEX);
        if (Phone == null){
            return false;
        }

            Matcher matcher = pattern.matcher(Phone);
            return matcher.matches();
    }

}
