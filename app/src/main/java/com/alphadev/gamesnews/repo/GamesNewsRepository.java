package com.alphadev.gamesnews.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.alphadev.gamesnews.room.GamesNewsDataBase;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;
import com.alphadev.gamesnews.room.model.Player;
import com.alphadev.gamesnews.room.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GamesNewsRepository {
    private final LiveData<List<com.alphadev.gamesnews.room.model.New>> mAllNews;
    private final LiveData<List<com.alphadev.gamesnews.room.model.New>> mAllFavoriteNews;
    //    private static FavoriteDao favoriteDao;
//    private final LiveData<List<String>> mNewsCategories;
//    private GamesNewsAPIService service;
    private Application application;
    //    private FavoriteNewsDao favoriteNewsDao;
    private static NewDao newDao;
    private static PlayerDao playerDao;
    private static UserDao userDao;

    public GamesNewsRepository(Application application) {
        this.application = application;
        GamesNewsDataBase db = GamesNewsDataBase.getDatabase(application);
        newDao = db.newDao();
        playerDao = db.playerDao();
        userDao = db.userDao();
        mAllNews = newDao.getAllNews();
        mAllFavoriteNews = newDao.getFavoritesNews();
    }


    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getAllNews() {
        return mAllNews;
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getFilteredNews(String filter) {
        return newDao.getFilteredNews(filter);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getNewsByCategory(String category) {
        return newDao.getNewsByCategory(category);
    }

    public LiveData<List<String>> getNewsCategories() {
        return newDao.getNewsCategory();
    }

    public LiveData<List<String>> getNewsImageByCategory(String category) {
        return newDao.getNewsImageByCategory(category);
    }

    public LiveData<List<Player>> getPlayersByCategory(String category) {
        return playerDao.getPlayersByCategory(category);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getFavoriteNews() {
        return mAllFavoriteNews;
    }


    public com.alphadev.gamesnews.room.model.New getNewById(String id) {
        com.alphadev.gamesnews.room.model.New n = null;
        AsyncTask<String, Void, com.alphadev.gamesnews.room.model.New> task = new AsyncTask<String, Void, com.alphadev.gamesnews.room.model.New>() {
            @Override
            protected com.alphadev.gamesnews.room.model.New doInBackground(String... strings) {
                com.alphadev.gamesnews.room.model.New n = null;
                n = newDao.getNewByID(strings[0]);
                return n;
            }
        };
        try {
            n = task.execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return n;
    }

    public User getCurrentUserInfo() {
        return userDao.getUser();
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
