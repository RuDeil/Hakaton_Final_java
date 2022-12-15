package com.example.hakatonfinaljava.boss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.net.NetModule;
import com.example.hakatonfinaljava.responses.Boss;
import com.example.hakatonfinaljava.responses.BossResponse;
import com.example.hakatonfinaljava.responses.LoginResponse;
import com.example.hakatonfinaljava.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;

public class BossActivity extends AppCompatActivity {

    private LoginResponse loginResponse = null;
    private Disposable single;
    private OkHttpClient client;
    private RecyclerView rvList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initToolbar();
        rvList = findViewById(R.id.rvList);
        initAdapter();

        loginResponse = (LoginResponse) getIntent().getSerializableExtra("KeyLoginResponse");

        NetModule netModule = new NetModule();

        String header = "bearer " +  loginResponse.getToken();
        netModule.getNetService().workerList(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::finishLoading)
                .doOnSuccess(this::handleResponse)
                .doOnError(error -> Utils.handleError(error, this))
                .subscribe();


    }

    private void initAdapter() {
        adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
        rvList.setAdapter(adapter);
    }

    private void handleResponse(Response<BossResponse> bossResponseResponse) {
        try {
            BossResponse response = Utils.handleResults(bossResponseResponse);
            List<Boss> list = response.getList();
            for(Boss boss:list){
                Log.e("TAG", "handleResponse: " + boss.getUsername() );
            }

        } catch (Throwable e) {
            Utils.handleError(e, this);
        }
    }

    private void finishLoading() {
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
}