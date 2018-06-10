package com.alphadev.gamesnews.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.room.model.Player;

import java.util.List;


@Dao
public interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(New n_new);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Player> player);

    @Query("DELETE FROM new")
    void deleteAll();

    @Query("DELETE FROM new where game=:category")
    void deleteByCategory(String category);

    @Query("SELECT * from new WHERE _id=:id")
    New getNewByID(String id);

    @Query("SELECT * from new ORDER BY created_date DESC")
    LiveData<List<New>> getAllNews();

    @Query("SELECT * FROM new WHERE favorite=1 ORDER BY created_date DESC")
    LiveData<List<New>> getFavoritesNews();

    @Query("SELECT * from new WHERE game=:category ORDER BY created_date DESC ")
    LiveData<List<New>> getNewsByCategory(String category);

    @Query("SELECT coverImage from new WHERE game=:category ORDER BY created_date DESC ")
    LiveData<List<String>> getNewsImageByCategory(String category);

    @Query("SELECT game from new GROUP BY game ORDER BY game DESC ")
    LiveData<List<String>> getNewsCategory();

    @Query("UPDATE new SET favorite=1 WHERE _id=:id")
    void setFavorite(String id);

    @Query("UPDATE new SET favorite=0 WHERE _id=:id")
    void unsetFavorite(String id);







}
