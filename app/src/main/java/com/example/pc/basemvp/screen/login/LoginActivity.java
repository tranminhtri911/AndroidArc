package com.example.pc.basemvp.screen.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;
import com.example.pc.basemvp.MainApplication;
import com.example.pc.basemvp.R;
import com.example.pc.basemvp.base.BaseActivity;
import com.example.pc.basemvp.data.model.User;
import com.example.pc.basemvp.data.source.remote.api.error.BaseException;
import com.example.pc.basemvp.databinding.ActivityLoginBinding;
import com.example.pc.basemvp.repositories.TokenRepository;
import com.example.pc.basemvp.repositories.UserRepository;
import com.example.pc.basemvp.utils.scheduler.BaseSchedulerProvider;
import com.example.pc.basemvp.widget.SuperRecyclerView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Login Screen.
 */
public class LoginActivity extends BaseActivity
        implements LoginContract.View, SuperRecyclerView.LoadDataListener {

    @Inject
    UserRepository mUserRepository;
    @Inject
    TokenRepository mTokenRepository;
    @Inject
    BaseSchedulerProvider mBaseSchedulerProvider;

    private LoginContract.Presenter mPresenter;

    private ActivityLoginBinding mBinding;
    private SuperRecyclerView mSuperRecyclerView;
    private UserAdapter mUserAdapter;
    private int d = 0;

    @Override
    protected void bundleData(Bundle savedInstanceState) {
    }

    @Override
    protected void initRootView(Bundle savedInstanceState) {
        ((MainApplication) getApplication()).getAppComponent().inject(this);
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        this.mBinding.setViewModel(this);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        this.mUserAdapter = new UserAdapter(this);

        this.mSuperRecyclerView = this.mBinding.superRecyclerView;
        this.mSuperRecyclerView.setLoadDataListener(this);
        this.mSuperRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mSuperRecyclerView.setAdapter(this.mUserAdapter);

        this.mPresenter = new LoginPresenter(this, this.mUserRepository, this.mTokenRepository, this.mBaseSchedulerProvider);
        this.mPresenter.doLogin("user25@yopmail.com", "12345678", 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onGetUserSuccess(User user) {
        this.mSuperRecyclerView.stopLoadData();
        this.mUserAdapter.updateData(fakeData());
    }

    @Override
    public void onGetError(BaseException exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore(int page) {
        this.mPresenter.doLogin("user25@yopmail.com", "12345678", 1);
    }

    @Override
    public void onRefreshData() {
        this.mPresenter.doLogin("user25@yopmail.com", "12345678", 1);
    }

    private List<User> fakeData() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            d += 1;
            user.setName("test " + d);
            list.add(user);
        }
        return list;
    }
}
