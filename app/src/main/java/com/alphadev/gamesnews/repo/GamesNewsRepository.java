package com.alphadev.gamesnews.repo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.remote.GamesNewsAPIUtils;
import com.alphadev.gamesnews.api.pojo.New;
import com.alphadev.gamesnews.room.GamesNewsDataBase;
import com.alphadev.gamesnews.room.dao.FavoriteNewsDao;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GamesNewsRepository {
    private GamesNewsAPIService service;
    private Application application;
    private FavoriteNewsDao favoriteNewsDao;
    private NewDao newDao;
    private PlayerDao playerDao;
    private UserDao userDao;

    public GamesNewsRepository(Application application) {
        this.application = application;
        GamesNewsDataBase db = GamesNewsDataBase.getDatabase(application);
        favoriteNewsDao = db.favoriteNewsDao();
        newDao = db.newDao();
        playerDao = db.playerDao();
        userDao = db.userDao();
        service = GamesNewsAPIUtils.getAPIService();
    }

    public List<New> getAllNews(String token) {


        AsyncTask<String, Void, List<New>> task = new AsyncTask<String, Void, List<New>>() {
            @Override
            protected List<New> doInBackground(String... strings) {
                 List<New> list = null;
                if (isOnline()) {

                    try {
                        list = service.getAllNews(strings[0]).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return list;
            }

        };

        try {
            return task.execute(token).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
