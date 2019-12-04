package com.luoj.githubdemo.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.elvishew.xlog.XLog;
import com.luoj.githubdemo.Injection;
import com.luoj.jetpackdemo.R;
import com.luoj.jetpackdemo.databinding.ActivityGithubSearchBinding;

public class GithubSearchActivity extends AppCompatActivity {

    final String LAST_SEARCH_QUERY = "last_search_query";

    ActivityGithubSearchBinding binding;
    GithubSearchViewModel viewModel;
    ReposAdapter adapter = new ReposAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_search);

        viewModel = ViewModelProviders.of(this, Injection.provideGithubSearchVMFactory(this)).get(GithubSearchViewModel.class);

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.list.addItemDecoration(decoration);
        binding.list.setAdapter(adapter);

        //监听列表数据，列表数据通过query连锁触发
        viewModel.repos.observe(this, repos -> {
            XLog.d("on repos changed.");
            displayEmptyView(null == repos || repos.isEmpty());
            adapter.submitList(repos);
        });

        viewModel.networkErrors.observe(this, s -> Toast.makeText(GithubSearchActivity.this, "Request data failed.", Toast.LENGTH_SHORT).show());

        String lastQuery = (null != savedInstanceState ? savedInstanceState.getString(LAST_SEARCH_QUERY, "Android") : "Android");
        viewModel.searchRepos(lastQuery);

        initSearchView(lastQuery);
    }

    /**
     * 初始化输入框的值和操作监听
     *
     * @param lastQuery
     */
    private void initSearchView(String lastQuery) {
        binding.etSearch.setText(lastQuery);
        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput();
                return true;
            }
            return false;
        });
        binding.etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput();
                return true;
            }
            return false;
        });
    }

    /**
     * 初始化列表并根据关键字查询数据
     */
    private void updateRepoListFromInput() {
        String query = binding.etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(query)) {
            binding.list.scrollToPosition(0);
            viewModel.searchRepos(query);
            adapter.submitList(null);//初始化适配器数据
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(LAST_SEARCH_QUERY, viewModel.getLastQueryString());
    }

    void displayEmptyView(boolean isEmpty) {
        binding.list.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        binding.emptyList.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

}
