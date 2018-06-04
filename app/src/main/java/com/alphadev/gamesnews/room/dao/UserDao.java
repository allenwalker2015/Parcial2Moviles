package com.alphadev.gamesnews.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alphadev.gamesnews.room.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * from user WHERE _id=:id")
    User getUserByID(String id);


}
