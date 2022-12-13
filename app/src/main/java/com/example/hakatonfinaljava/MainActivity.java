package com.example.hakatonfinaljava;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.redmadrobot.inputmask.helper.Mask;
import com.redmadrobot.inputmask.model.CaretString;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                        intent = new Intent(MainActivity.this, Boss.class);
                        startActivity(intent);
                            }
                        else{
                        intent = new Intent(MainActivity.this, Client.class);
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
                    intent = new Intent(MainActivity.this, Registration.class);
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
        return m.matches();
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

    public static String formatPhone(String param) {
        Mask mask = new Mask("7 [000] [000] [00] [00]");
        Mask.Result result = mask.apply(
                new CaretString(
                        param,
                        param.length(),
                        new CaretString.CaretGravity.BACKWARD(false)
                )
        );
        return result.getFormattedText().getString();
    }


    private void getData() {
        String loginNumber = formatPhone(etLoginNumber.getText().toString());
        String loginPassword = etLoginPass.getText().toString();
        startLoading();
        single = (Disposable) getNetService().getData("science")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::finishLoading)
                .subscribe(this::handleResults, this::handleError);


    }


    private void handleError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
    private void handleResults(Response<News> response) {
        if (response.isSuccessful()) {
            News news = (News) response.body();
            if (news != null) {
                Log.e("TAG", "isSuccess:" + news.getSuccess());
                List<Data> dataList = news.data;
                if (dataList != null) {
                    for (Data data : dataList) {
                        Log.e("TAG", "News:\n" +
                                "author: " + data.getAuthor() + "\n" +
                                "content: " + data.getContent() + "\n");
                    }
                }
                single.dispose();
            } else {
                Throwable throwable = new Throwable("news in null");
                handleError(throwable);
            }
        } else {
            String errorMessage = response.message();
            Throwable throwable = new Throwable(errorMessage);
            handleError(throwable);
        }
    }

    private void startLoading() {

    }

    private void finishLoading() {

    }
    private NetApi getNetService() {
        String baseUrl = "https://inshorts.deta.dev/"; // todo change
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(NetApi.class);
    }





}
