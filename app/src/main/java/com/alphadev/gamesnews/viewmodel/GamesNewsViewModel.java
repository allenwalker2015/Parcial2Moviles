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

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getAllNews(){
        return mRepository.getAllNews();
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getAllNewsByCategory(String category) {
        return mRepository.getNewsByCategory(category);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.Player>> getPlayersByCategory(String category) {
        return mRepository.getPlayersByCategory(category);
    }


    public boolean updateNews(String token){
        return mRepository.updateNews(token);
    }

    public boolean updateNewsByCategory(String token, String category) {
        return mRepository.updateNews(token);
    }

    public boolean updateTopPlayersByCategory(String token, String category) {
        return mRepository.updatePlayersByCategory(token, category);
    }
}
