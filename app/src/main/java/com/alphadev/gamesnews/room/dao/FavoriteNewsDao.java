package com.alphadev.gamesnews.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alphadev.gamesnews.room.model.FavoriteNews;
import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.room.model.User;

import java.util.List;

@Dao
public interface FavoriteNewsDao {

    @Insert
    void insert(FavoriteNews favoriteNews);

    @Query("DELETE FROM favorite_news")
    void deleteAll();

    @Query("SELECT * from favorite_news WHERE id=:id")
    FavoriteNews getFavoriteByID(String id);

    @Query("SELECT * FROM new INNER JOIN favorite_news ON new._id=favorite_news.id WHERE favorite_news.idusuario=:id")
    List<New> getFavoriteNews(int id);


}
