package com.alphadev.gamesnews.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.alphadev.gamesnews.repo.GamesNewsRepository;

import java.util.List;

public class GamesNewsViewModel extends AndroidViewModel {
    private final GamesNewsRepository mRepository;

    public GamesNewsViewModel(@NonNull Application application) {
            super(application);
            mRepository = new GamesNewsRepository(application);
    }

    public boolean addFavorite(String token, String user, String n_new) {
        return mRepository.addFavorite(token, user, n_new);
    }

    public boolean removeFavorite(String token, String user, String n_new) {
        return mRepository.removeFavorite(token, user, n_new);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getAllNews(){
        return mRepository.getAllNews();
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getAllNewsByCategory(String category) {
        return mRepository.getNewsByCategory(category);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getFavoriteNews() {
        return mRepository.getFavoriteNews();
    }


    public LiveData<List<String>> getAllNewsImageByCategory(String category) {
        return mRepository.getNewsImageByCategory(category);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.Player>> getPlayersByCategory(String category) {
        return mRepository.getPlayersByCategory(category);
    }

    public boolean updateNews(String token){
        return mRepository.updateNews(token);
    }

    public boolean updateUserInfo(String token) {
        return mRepository.updateUserInfo(token);
    }

    public boolean updateUserInfoNoAsync(String token) {
        return mRepository.updateUserInfoNoAsync(token);
    }

    public boolean updateNewsByCategory(String token, String category) {
        return mRepository.updateNews(token);
    }

    public boolean updateTopPlayersByCategory(String token, String category) {
        return mRepository.updatePlayersByCategory(token, category);
    }
}
