package com.example.hakatonfinaljava;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.widget.Toolbar;

import com.redmadrobot.inputmask.helper.Mask;
import com.redmadrobot.inputmask.model.CaretString;

import io.reactivex.android.schedulers.AndroidSchedulers;


import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client;

    private EditText etLoginNumber, etLoginPass;
    private Button btnLogin;
    private Disposable single;
    private Button btnRegister;
    private Boolean isBoss = false;
    private View vPhone, vPass;


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
        vPhone = findViewById(R.id.textInputLayoutNumber);
        vPass = findViewById(R.id.textInputLayoutPass);
    }

    private void onClick(View v) {

        {
            Intent intent;
            switch (v.getId()) {

                case R.id.btnLogin:
                    getData();
                    if(isBoss) {
                        intent = new Intent(MainActivity.this, Boss.class);
                        startActivity(intent);
                    }
                    else{
                        intent = new Intent(MainActivity.this, Client.class);
                        startActivity(intent);
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
    public static String formatPhone(String param) {
        Mask mask = new Mask("+7 ([000]) [000] [00] [00]");
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
       /* if(loginNumber.length()<12){
            //vPhone.setContentDescription("Номер должен быть больше 12 символов");


        }
        TODO обработчик ошибки ввода логина
        */
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
