package com.luoj.jetpackdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.luoj.jetpackdemo.AppContext;
import com.luoj.jetpackdemo.data.db.UserDao;
import com.luoj.jetpackdemo.data.model.User;

public class UserListViewModel extends AndroidViewModel {

    public final LiveData<PagedList<User>> userPagedList;
    public UserDao userDao;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.userDao = ((AppContext) application).getDataBase().getUserDao();
        this.userPagedList = new LivePagedListBuilder<>(userDao.users(), 50).build();
    }

//    public static class Factory implements ViewModelProvider.Factory {
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            if (modelClass.isAssignableFrom(UserListViewModel.class)) {
//                return (T) new UserListViewModel(null);
//            }
//            throw new RuntimeException("unknown class -> " + modelClass.getName());
//        }
//    }

}
