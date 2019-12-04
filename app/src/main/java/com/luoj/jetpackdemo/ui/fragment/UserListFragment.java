package com.luoj.jetpackdemo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.luoj.jetpackdemo.R;
import com.luoj.jetpackdemo.data.model.User;
import com.luoj.jetpackdemo.databinding.FragmentUserListBinding;
import com.luoj.jetpackdemo.ui.adapter.UserListAdapter;
import com.luoj.jetpackdemo.viewmodel.UserListViewModel;

public class UserListFragment extends Fragment implements UserListAdapter.OnItemClick {

    UserListAdapter adapter = new UserListAdapter();
    UserListViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vm = ViewModelProviders.of(this).get(UserListViewModel.class);
        vm.userPagedList.observe(this,adapter::submitList);

        FragmentUserListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false);
        binding.setAdapter(adapter);
        adapter.setOnItemClick(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(User user) {
        UserListFragmentDirections.ActionUserListToInfo action = UserListFragmentDirections.actionUserListToInfo(user);
        NavHostFragment.findNavController(this).navigate(action);
    }

}
