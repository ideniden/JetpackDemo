package com.luoj.jetpackdemo.data.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.luoj.jetpackdemo.data.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY uid ASC")
    DataSource.Factory<Integer, User> users();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

}
