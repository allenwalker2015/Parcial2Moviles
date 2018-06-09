package com.alphadev.gamesnews.room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.alphadev.gamesnews.room.dao.FavoriteDao;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;
import com.alphadev.gamesnews.room.model.Favorite;
import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.room.model.Player;
import com.alphadev.gamesnews.room.model.User;

@Database(entities = {New.class, Player.class, User.class, Favorite.class}, version = 1)
public abstract class GamesNewsDataBase extends RoomDatabase {


//    public abstract FavoriteNewsDao favoriteNewsDao();
    public abstract NewDao newDao();
    public  abstract PlayerDao playerDao();
    public  abstract UserDao userDao();

    public abstract FavoriteDao favoriteDao();
    private static GamesNewsDataBase INSTANCE;


    public static GamesNewsDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GamesNewsDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GamesNewsDataBase.class, "games_news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
