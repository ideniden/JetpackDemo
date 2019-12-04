package com.luoj.githubdemo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.luoj.githubdemo.model.Repo;

@Database(entities = {Repo.class}, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    public abstract RepoDao reposDao();

    private static volatile RepoDatabase INSTANCE;

    public static RepoDatabase getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (RepoDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = build(context);
                }
            }
        }
        return INSTANCE;
    }

    private static RepoDatabase build(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), RepoDatabase.class, "Github.db").build();
    }

}
