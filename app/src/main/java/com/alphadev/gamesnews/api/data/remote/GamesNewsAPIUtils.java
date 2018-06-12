package com.alphadev.gamesnews.api.data.remote;

import android.content.Context;

import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.RetrofitClient;

public class GamesNewsAPIUtils {


        private GamesNewsAPIUtils() {}

        public static final String BASE_URL = "http://gamenewsuca.herokuapp.com/";

    public static GamesNewsAPIService getAPIService(Context context) {

        return RetrofitClient.getClient(BASE_URL, context).create(GamesNewsAPIService.class);
        }
}
