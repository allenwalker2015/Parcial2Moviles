package com.alphadev.gamesnews.api.data.remote;

import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.RetrofitClient;

public class GamesNewsAPIUtils {


        private GamesNewsAPIUtils() {}

        public static final String BASE_URL = "http://gamenewsuca.herokuapp.com/";

        public static GamesNewsAPIService getAPIService() {

            return RetrofitClient.getClient(BASE_URL).create(GamesNewsAPIService.class);
        }
}
