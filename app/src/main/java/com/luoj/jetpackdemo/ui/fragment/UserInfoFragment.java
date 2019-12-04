package com.luoj.jetpackdemo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.luoj.jetpackdemo.R;
import com.luoj.jetpackdemo.data.model.User;
import com.luoj.jetpackdemo.databinding.FragmentUserInfoBinding;

public class UserInfoFragment extends Fragment {

    FragmentUserInfoBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        User user = UserInfoFragmentArgs.fromBundle(getArguments()).getUser();
        if (null != user) {
            binding.setUser(user);
        } else {
            binding.tvName.setText("");
        }
    }

}
