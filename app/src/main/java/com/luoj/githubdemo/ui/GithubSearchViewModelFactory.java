package com.luoj.githubdemo.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.luoj.githubdemo.data.GithubRepository;

public class GithubSearchViewModelFactory implements ViewModelProvider.Factory {

    GithubRepository githubRepository;

    public GithubSearchViewModelFactory(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GithubSearchViewModel.class)){
            return (T) new GithubSearchViewModel(githubRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
