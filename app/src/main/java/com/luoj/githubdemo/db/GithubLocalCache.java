package com.luoj.githubdemo.db;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.elvishew.xlog.XLog;
import com.luoj.githubdemo.model.Repo;

import java.util.List;
import java.util.concurrent.Executor;

public class GithubLocalCache {

    RepoDao repoDao;
    Executor ioExecutor;

    public GithubLocalCache(RepoDao repoDao, Executor ioExecutor) {
        this.repoDao = repoDao;
        this.ioExecutor = ioExecutor;
    }

    /**
     * 从数据库中按关键字查询数据，返回LiveData。将空格转换成%用以模拟GithubAPI多个关键字查询
     *
     * @param queryString
     * @return
     */
    public DataSource.Factory<Integer, Repo> reposByName(@NonNull String queryString) {
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(queryString.replace(" ", "%"));
        sb.append("%");
        XLog.d("reposByName(query:%s)", sb.toString());
        return repoDao.reposByName(sb.toString());
    }

    public void insert(@NonNull List<Repo> repos, Callback callback) {
        ioExecutor.execute(() -> {
            XLog.d("insert repos");
            repoDao.insert(repos);
            callback.insertFinished();
        });
    }

    public interface Callback{
        void insertFinished();
    }

}
