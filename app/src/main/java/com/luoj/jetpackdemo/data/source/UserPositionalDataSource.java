package com.luoj.jetpackdemo.data.source;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.luoj.jetpackdemo.data.model.User;

public class UserPositionalDataSource extends PositionalDataSource<User> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<User> callback) {

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<User> callback) {

    }

}
