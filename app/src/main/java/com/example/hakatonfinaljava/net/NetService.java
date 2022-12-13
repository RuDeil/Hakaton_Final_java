package com.example.hakatonfinaljava.net;

import com.example.hakatonfinaljava.models.LoginData;
import com.example.hakatonfinaljava.models.RegistrationData;
import com.example.hakatonfinaljava.responses.LoginResponse;
import com.example.hakatonfinaljava.responses.RegistrationResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetService {
    @POST("/auth/registration")
    Single<Response<RegistrationResponse>> registration(@Body RegistrationData body);

    @POST("/auth/login")
    Single<Response<LoginResponse>> login(@Body LoginData body);
}
