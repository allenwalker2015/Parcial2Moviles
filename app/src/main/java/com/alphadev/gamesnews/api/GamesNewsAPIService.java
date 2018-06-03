package com.alphadev.gamesnews.api;

import com.alphadev.gamesnews.model.Token;
import com.alphadev.gamesnews.model.Usuario;


import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GamesNewsAPIService {

        @POST("/login")
        @FormUrlEncoded
        Call<Token> logIn(@Field("user") String user,
                             @Field("password") String password );

        @GET("/users")
        Call<List<Usuario>> getUsuarios(@Header("Authorization") String authHeader);

        @GET("/users")
        Call<ResponseBody> getUsuarios2(@Header("Authorization") String authHeader);


}

