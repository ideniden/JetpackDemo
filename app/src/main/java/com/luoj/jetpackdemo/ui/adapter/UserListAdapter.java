package com.luoj.jetpackdemo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.luoj.jetpackdemo.R;
import com.luoj.jetpackdemo.data.model.User;
import com.luoj.jetpackdemo.databinding.ItemUserBinding;

public class UserListAdapter extends PagedListAdapter<User, UserListAdapter.UserListHolder> {

    public UserListAdapter() {
        super(diffCallback);
    }

    private static DiffUtil.ItemCallback<User> diffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.uid == newItem.uid;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public UserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user, parent, false);
        return new UserListHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserListHolder holder, int position) {

        final User item = getItem(position);

        ItemUserBinding binding = DataBindingUtil.findBinding(holder.itemView);
        binding.setUser(item);
        binding.getRoot().setOnClickListener(v -> {
            if (null != onItemClick) {
                onItemClick.onItemClick(item);
            }
        });
    }

    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(User user);
    }

    public class UserListHolder extends RecyclerView.ViewHolder {
        public UserListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
