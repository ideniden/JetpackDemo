package com.luoj.jetpackdemo.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.luoj.jetpackdemo.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {

    public abstract UserDao getUserDao();

}
