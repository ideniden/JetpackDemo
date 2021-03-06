package com.luoj.githubdemo.ui;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.luoj.githubdemo.model.Repo;
import com.luoj.jetpackdemo.R;
import com.luoj.jetpackdemo.databinding.ItemGithubRepoBinding;

public class ReposAdapter extends ListAdapter<Repo, RepoViewHolder> {

    protected ReposAdapter() {
        super(REPO_COMPARATOR);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGithubRepoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_github_repo, parent, false);
        return new RepoViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(DataBindingUtil.findBinding(holder.itemView), getItem(position));
    }

    private static DiffUtil.ItemCallback REPO_COMPARATOR = new DiffUtil.ItemCallback<Repo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Repo oldItem, @NonNull Repo newItem) {
            return TextUtils.equals(oldItem.fullName, newItem.fullName);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Repo oldItem, @NonNull Repo newItem) {
            return oldItem.equals(newItem);
        }
    };

}
