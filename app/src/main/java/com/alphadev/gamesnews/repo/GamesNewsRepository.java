package com.alphadev.gamesnews.repo;

import android.app.Application;

import com.alphadev.gamesnews.room.GamesNewsDataBase;
import com.alphadev.gamesnews.room.dao.FavoriteNewsDao;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;

public class GamesNewsRepository {

    private FavoriteNewsDao favoriteNewsDao;
    private NewDao newDao;
    private PlayerDao playerDao;
    private UserDao userDao;

    public GamesNewsRepository(Application application) {
        GamesNewsDataBase db = GamesNewsDataBase.getDatabase(application);
        favoriteNewsDao = db.favoriteNewsDao;
        newDao = db.newDao;
        playerDao = db.playerDao;
        userDao = db.userDao;
    }


}
