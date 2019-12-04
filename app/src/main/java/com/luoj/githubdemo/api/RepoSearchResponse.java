package com.luoj.githubdemo.api;

import com.google.gson.annotations.SerializedName;
import com.luoj.githubdemo.model.Repo;

import java.util.List;

public class RepoSearchResponse {

    @SerializedName("total_count")
    public Integer total;

    @SerializedName("items")
    public List<Repo> items;

    Integer nextPage;

    public int getListCount() {
        return null != items && !items.isEmpty() ? items.size() : 0;
    }

}
