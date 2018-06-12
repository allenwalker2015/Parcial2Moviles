package com.alphadev.gamesnews.api.data;

import android.content.Context;
import android.content.Intent;

import com.alphadev.gamesnews.activities.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, final Context context) {
        if (retrofit==null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            okhttp3.Response response = chain.proceed(request);

                            // todo deal with the issues the way you need to
                            if (response.code() == 500) {
                                context.startActivity(
                                        new Intent(
                                                context,
                                                LoginActivity.class
                                        )
                                );

                                return response;
                            }

                            return response;
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}