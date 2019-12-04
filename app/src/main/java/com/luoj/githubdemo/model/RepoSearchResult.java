package com.luoj.githubdemo.model;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class RepoSearchResult {

    public LiveData<PagedList<Repo>> data;
    public LiveData<String> networkError;

    public RepoSearchResult(LiveData<PagedList<Repo>> data, LiveData<String> networkError) {
        this.data = data;
        this.networkError = networkError;
    }
}
