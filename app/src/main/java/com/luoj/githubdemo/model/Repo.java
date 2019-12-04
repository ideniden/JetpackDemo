package com.luoj.githubdemo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "repos")
public class Repo {

    @PrimaryKey
    @SerializedName("id")
    public Long id;

    @SerializedName("name")
    public String name;

    @SerializedName("full_name")
    public String fullName;

    @SerializedName("description")
    public String description;

    @SerializedName("html_url")
    public String url;

    @SerializedName("stargazers_count")
    public Integer stars;

    @SerializedName("forks_count")
    public Integer forks;

    @SerializedName("language")
    public String language;

}
