package com.example.hakatonfinaljava;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;

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
    private TextView textView;
    private EditText etLoginNumber, etLoginPass;
    private Button btnLogin;
    private Disposable single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLoginNumber = findViewById(R.id.ETLoginNumber);
        etLoginPass = findViewById(R.id.ETLoginPass);
        btnLogin = findViewById(R.id.btnLogin);
        textView = findViewById(R.id.result);



btnLogin.setOnClickListener(view -> getData());
    }

    private void getData() {
        String loginNumber = etLoginNumber.getText().toString();
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