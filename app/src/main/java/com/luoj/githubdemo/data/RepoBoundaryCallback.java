package com.luoj.githubdemo.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.elvishew.xlog.XLog;
import com.luoj.githubdemo.api.GithubService;
import com.luoj.githubdemo.api.RepoSearchResponse;
import com.luoj.githubdemo.db.GithubLocalCache;
import com.luoj.githubdemo.model.Repo;

import rx.Subscriber;

public class RepoBoundaryCallback extends PagedList.BoundaryCallback<Repo> {

    String query;

    GithubService service;
    GithubLocalCache cache;

    final int NETWORK_PAGE_SIZE = 50;

    private int lastRequestedPage = 1;

    private boolean isRequestInProgress = false;

    public MutableLiveData networkErrors = new MutableLiveData<String>();

    public RepoBoundaryCallback(String query, GithubService service, GithubLocalCache cache) {
        this.query = query;
        this.service = service;
        this.cache = cache;
    }

    @Override
    public void onZeroItemsLoaded() {
        requestAndSaveData(query);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Repo itemAtEnd) {
        requestAndSaveData(query);
    }

    private void requestAndSaveData(String query) {
        if (isRequestInProgress) return;

        isRequestInProgress = true;

        GithubService.searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, new Subscriber<RepoSearchResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                XLog.d("searchRepos failed.\n" + e);
                networkErrors.postValue(e.toString());
                isRequestInProgress = false;
            }

            @Override
            public void onNext(RepoSearchResponse repoSearchResponse) {
                XLog.d("searchRepos success.data size -> %s", repoSearchResponse.getListCount());
                cache.insert(repoSearchResponse.items, () -> {
                    lastRequestedPage += 1;
                    isRequestInProgress = false;
                });
            }
        });

    }

}
