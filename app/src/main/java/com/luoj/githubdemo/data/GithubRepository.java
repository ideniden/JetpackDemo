package com.luoj.githubdemo.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.luoj.githubdemo.api.GithubService;
import com.luoj.githubdemo.db.GithubLocalCache;
import com.luoj.githubdemo.model.Repo;
import com.luoj.githubdemo.model.RepoSearchResult;

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

    final int DATABASE_PAGE_SIZE = 20;


    public RepoSearchResult search(String query) {

        DataSource.Factory<Integer, Repo> dataSourceFactory = cache.reposByName(query);

        RepoBoundaryCallback repoBoundaryCallback = new RepoBoundaryCallback(query, service, cache);

        LiveData<PagedList<Repo>> data = new LivePagedListBuilder<>(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(repoBoundaryCallback)
                .build();

        return new RepoSearchResult(data, repoBoundaryCallback.networkErrors);
    }

}
