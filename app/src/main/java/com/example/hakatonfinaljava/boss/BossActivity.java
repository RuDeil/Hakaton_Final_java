package com.example.hakatonfinaljava.boss;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.hakatonfinaljava.R;
import com.example.hakatonfinaljava.net.NetModule;
import com.example.hakatonfinaljava.responses.Boss;
import com.example.hakatonfinaljava.responses.BossResponse;
import com.example.hakatonfinaljava.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_layout);
        initToolbar();

        NetModule netModule = new NetModule();
        netModule.getNetService().workerList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::finishLoading)
                .doOnSuccess(this::handleResponse)
                .doOnError(error -> Utils.handleError(error, this))
                .subscribe();


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