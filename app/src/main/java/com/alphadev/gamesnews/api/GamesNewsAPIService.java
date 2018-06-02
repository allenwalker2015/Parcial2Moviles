package com.alphadev.gamesnews.api;

import com.alphadev.gamesnews.model.Token;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GamesNewsAPIService {

        @POST("/login")
        @FormUrlEncoded
        Call<Token> logIn(@Field("user") String user,
                             @Field("password") String password );


}

