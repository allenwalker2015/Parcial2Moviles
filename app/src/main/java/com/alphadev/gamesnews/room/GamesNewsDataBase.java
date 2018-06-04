package com.alphadev.gamesnews.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.alphadev.gamesnews.room.dao.FavoriteNewsDao;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;

@Database(entities = {FavoriteNewsDao.class, NewDao.class, PlayerDao.class, UserDao.class}, version = 1)
public abstract class GamesNewsDataBase extends RoomDatabase {

    private static GamesNewsDataBase INSTANCE;
    public  FavoriteNewsDao favoriteNewsDao;
    public  NewDao newDao;
    public  PlayerDao playerDao;
    public  UserDao userDao;


    public static GamesNewsDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GamesNewsDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GamesNewsDataBase.class, "word_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
