package com.luoj.githubdemo.api;

import com.elvishew.xlog.XLog;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Github Http API
 */
public interface GithubService {

    String BASE_URL = "https://api.github.com/";
    String IN_QUALIFIER = "in:name,description";

    @GET("search/repositories?sort=stars")
    Observable<RepoSearchResponse> searchRepos(
            @Query("q") String query,
            @Query("page") int page,
            @Query("per_page") int itemsPerPage
    );

    static void searchRepos(GithubService service, String query, int page, int itemsPerPage, Subscriber<RepoSearchResponse> subscriber) {

        XLog.d("searchRepos(query:%s,page:%s,itemsPerPage:%s)", query, page, itemsPerPage);

        String apiQuery = query + IN_QUALIFIER;

        service.searchRepos(apiQuery, page, itemsPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//need rxandroid
                .subscribe(subscriber);
    }

    static GithubService create() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.baseUrl(BASE_URL);
        retrofit.client(client);
        retrofit.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        retrofit.addConverterFactory(GsonConverterFactory.create());
        return retrofit.build().create(GithubService.class);
    }

}
