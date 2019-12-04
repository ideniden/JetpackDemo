package com.luoj.jetpackdemo.data.repo;

import androidx.paging.PagedList;

import com.luoj.jetpackdemo.data.db.UserDao;
import com.luoj.jetpackdemo.data.model.User;
import com.luoj.jetpackdemo.net.http.HttpApi;

public class UserRepo {

    HttpApi httpApi;
    UserDao userDao;

    public PagedList<User> getUser(){
        return null;
    }

}