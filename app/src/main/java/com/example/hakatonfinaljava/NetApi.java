package com.example.hakatonfinaljava;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetApi {
    @GET("news")
    Single<Response<News>> getData(@Query("category") String categoryName);

    @POST("")
    Single<Response<Object>> postData(@Body Object value);
}
