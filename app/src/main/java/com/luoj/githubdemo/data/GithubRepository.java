package com.luoj.githubdemo.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elvishew.xlog.XLog;
import com.luoj.githubdemo.api.GithubService;
import com.luoj.githubdemo.api.RepoSearchResponse;
import com.luoj.githubdemo.db.GithubLocalCache;
import com.luoj.githubdemo.model.Repo;
import com.luoj.githubdemo.model.RepoSearchResult;

import java.util.List;

import rx.Subscriber;

/**
 * 数据仓库
 * 提供唯一可信数据来源：RepoSearchResult.data
 */
public class GithubRepository {

    GithubService service;
    GithubLocalCache cache;

    public GithubRepository(GithubService service, GithubLocalCache cache) {
        this.service = service;
        this.cache = cache;
    }

    final int NETWORK_PAGE_SIZE = 50;

    private int lastRequestedPage = 1;

    private boolean isRequestInProgress = false;

    private MutableLiveData networkErrors = new MutableLiveData<String>();

    public RepoSearchResult search(String query) {
        lastRequestedPage = 1;

        requestAndSaveData(query);

        LiveData<List<Repo>> data = cache.reposByName(query);

        return new RepoSearchResult(data, networkErrors);
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

    public void requestMore(String query) {
        XLog.d("requestMore(query:%s),page:%s", query, lastRequestedPage);
        requestAndSaveData(query);
    }

}
