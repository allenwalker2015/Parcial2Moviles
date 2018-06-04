package com.alphadev.gamesnews.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.alphadev.gamesnews.api.pojo.New;
import com.alphadev.gamesnews.repo.GamesNewsRepository;

import java.util.List;

public class GamesNewsViewModel extends AndroidViewModel {
    private final GamesNewsRepository mRepository;

    public GamesNewsViewModel(@NonNull Application application) {
            super(application);
            mRepository = new GamesNewsRepository(application);
            //mAllWords = mRepository.getAllWords();

    }

    public List<New>getAllNews(String token){
       return mRepository.getAllNews(token);
    }
}
