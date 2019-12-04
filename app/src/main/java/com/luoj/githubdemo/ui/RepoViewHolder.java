package com.luoj.githubdemo.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luoj.githubdemo.model.Repo;
import com.luoj.jetpackdemo.databinding.ItemGithubRepoBinding;

public class RepoViewHolder extends RecyclerView.ViewHolder {

    public RepoViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(ItemGithubRepoBinding binding, Repo repo){
        binding.setRepo(repo);
    }

}
