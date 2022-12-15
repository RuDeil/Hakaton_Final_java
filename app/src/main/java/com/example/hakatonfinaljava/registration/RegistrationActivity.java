package com.example.hakatonfinaljava.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.main.MainActivity;
import com.example.hakatonfinaljava.models.RegistrationData;
import com.example.hakatonfinaljava.net.NetModule;
import com.example.hakatonfinaljava.responses.RegistrationResponse;
import com.example.hakatonfinaljava.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText FioReg, ETLoginNumber, ETLoginPass, ETLoginPassAgain;
    TextInputLayout FioLayout, NumberLayout, PassLayout, PassReturnLayout;
    AppCompatButton btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        FioReg = findViewById(R.id.FioReg);
        ETLoginNumber = findViewById(R.id.ETLoginNumber);
        ETLoginPass = findViewById(R.id.ETLoginPass);
        ETLoginPassAgain = findViewById(R.id.ETLoginPassAgain);
        FioLayout = findViewById(R.id.FioLayout);
        NumberLayout = findViewById(R.id.NumberLayout);
        PassLayout = findViewById(R.id.PassLayout);
        PassReturnLayout = findViewById(R.id.PassReturnLayout);
        btnReg = findViewById(R.id.btnReg);

        initToolbar();
        initListeners();
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

    TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            setFormatText(ETLoginNumber, loginTextWatcher, Utils.formatPhone(editable.toString())); // todo cringe fix pls
        }
    };

    private void initListeners() {
        ETLoginNumber.addTextChangedListener(loginTextWatcher);
        btnReg.setOnClickListener(view -> sendRegistrationRequest());
    }

    private void sendRegistrationRequest() {
        String fio = FioReg.getText().toString();
        String phoneNumber = ETLoginNumber.getText().toString();
        String pass = ETLoginPass.getText().toString();
        String passAgain = ETLoginPassAgain.getText().toString(); // todo

        ETLoginPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidPassword(s.toString())){
                    PassLayout.setErrorEnabled(true);
                    String error = "Недопустимые символыю Допускаются латинские буквыБ цифры и спецсимволы ";
                    PassLayout.setError(error);


                } else {
                    PassLayout.setErrorEnabled(false);
                    String error = "";
                    PassLayout.setError(error);
                }
            }
        });
        ETLoginPassAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ETLoginPass.getText().toString() == s.toString()){
                    PassReturnLayout.setErrorEnabled(true);
                    String error = "Пароли должны совпадать" ;
                    PassReturnLayout.setError(error);


                } else {
                    PassLayout.setErrorEnabled(false);
                    String error = "";
                    PassLayout.setError(error);
                }
            }
        });

        NetModule netModule = new NetModule();
        RegistrationData registrationData = new RegistrationData(phoneNumber, fio, pass);
        startLoading();
        netModule.getNetService().registration(registrationData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::finishLoading)
                .doOnSuccess(this::handleResponse)
                .doOnError(error -> Utils.handleError(error, this))
                .subscribe();
    }

    private void handleResponse(Response<RegistrationResponse> result) {
        try {
            Utils.handleResults(result);
            openLoginScreen();
        } catch (Throwable e) {
            Utils.handleError(e, this);
        }
    }

    private void openLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void startLoading() {
    }

    private void finishLoading() {
    }

    private void setFormatText(EditText editText, TextWatcher textWatcher, String text) {
        editText.removeTextChangedListener(textWatcher);
        editText.setText(text);
        editText.addTextChangedListener(textWatcher);
    }
    public static boolean isValidPassword(final String password) {

        String regex = ("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?()@#$%^&+=])(?=\\S+$).{8,}$");
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        else {
            Matcher m = p.matcher(password);
            return m.matches();
        }
    }
}



