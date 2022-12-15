package com.example.hakatonfinaljava.net;

import com.example.hakatonfinaljava.models.LoginData;
import com.example.hakatonfinaljava.models.RegistrationData;
import com.example.hakatonfinaljava.responses.BaseResponse;
import com.example.hakatonfinaljava.responses.BossResponse;
import com.example.hakatonfinaljava.responses.LoginResponse;
import com.example.hakatonfinaljava.models.OnlineData;
import com.example.hakatonfinaljava.responses.LogoutRespounse;
import com.example.hakatonfinaljava.responses.RegistrationResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetService {
    @POST("registration")
    Single<Response<RegistrationResponse>> registration(@Body RegistrationData body);

    @POST("login")
    Single<Response<LoginResponse>> login(@Body LoginData body);

    @POST("online")
    Single<Response<BaseResponse>> online(@Body OnlineData body);

    @GET("workerList")
    Single<Response<BossResponse>> workerList();

    @GET("logout")
    Single<Response<LogoutRespounse>> logout();
}
