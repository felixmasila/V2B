package com.felixmasila.v2b.activity;

import com.felixmasila.v2b.models.ServerRequest;
import com.felixmasila.v2b.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("virtualblood/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
