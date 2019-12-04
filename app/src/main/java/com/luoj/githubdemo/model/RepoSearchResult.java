package com.luoj.githubdemo.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepoSearchResult {

    public LiveData<List<Repo>> data;
    public LiveData<String> networkError;

    public RepoSearchResult(LiveData<List<Repo>> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }
}
