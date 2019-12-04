package com.luoj.githubdemo;

import android.content.Context;

import com.luoj.githubdemo.api.GithubService;
import com.luoj.githubdemo.data.GithubRepository;
import com.luoj.githubdemo.db.GithubLocalCache;
import com.luoj.githubdemo.db.RepoDatabase;
import com.luoj.githubdemo.ui.GithubSearchViewModelFactory;

import java.util.concurrent.Executors;

/**
 * 实现各对象的构造和参数注入过程
 */
public class Injection {

    public static GithubSearchViewModelFactory provideGithubSearchVMFactory(Context context){
        return new GithubSearchViewModelFactory(provideGithubRepository(context));
    }

    private static GithubRepository provideGithubRepository(Context context) {
        return new GithubRepository(GithubService.create(),provideCache(context));
    }

    private static GithubLocalCache provideCache(Context context) {
        return new GithubLocalCache(RepoDatabase.getInstance(context).reposDao(), Executors.newSingleThreadExecutor());
    }

}
