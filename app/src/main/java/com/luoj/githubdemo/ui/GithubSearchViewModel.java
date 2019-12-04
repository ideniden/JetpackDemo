package com.luoj.githubdemo.ui;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.luoj.githubdemo.data.GithubRepository;
import com.luoj.githubdemo.model.Repo;
import com.luoj.githubdemo.model.RepoSearchResult;

import java.util.List;

public class GithubSearchViewModel extends ViewModel {

    public static final int VISIBLE_THRESHOLD = 5;

    GithubRepository repository;

    public GithubSearchViewModel(GithubRepository repository) {
        this.repository = repository;
    }

    private MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    private LiveData<RepoSearchResult> repoResult = Transformations.map(queryLiveData, (input) -> {
        return repository.search(input);
    });

    /**
     * repos监听repoResult的同时也监听repoResult.data
     * 对于repoResult的监听，repoResult每次search后会new新的对象，但repoResult.data还会是room组件返回的唯一livedata对象
     * room组件检测到数据库变化时，会通知repoResult.data，因为repos也监听了repoResult.data，所以repos会触发通知
     */
    public LiveData<List<Repo>> repos = Transformations.switchMap(repoResult, (repoResult) -> {
        return repoResult.data;
    });

    public LiveData<String> networkErrors = Transformations.switchMap(repoResult, (repoResult) -> {
        return repoResult.networkError;
    });


    public void searchRepos(String query) {
        queryLiveData.postValue(query);
    }

    /**
     * 执行条件：可见总数 + 可见位置 + 阈值 >= 数据总数
     * 不等式逻辑：滑过列表一半时触发加载更多数据
     * @param visibleItemCount
     * @param lastVisibleItemPosition
     * @param totalItemCount
     */
    public void listScrolled(int visibleItemCount, int lastVisibleItemPosition, int totalItemCount) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            String lastQueryString = getLastQueryString();
            if (!TextUtils.isEmpty(lastQueryString)) {
                repository.requestMore(lastQueryString);
            }
        }
    }

    public String getLastQueryString() {
        return queryLiveData.getValue();
    }

}
