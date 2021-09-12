package com.example.pc.basemvp.screen.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.pc.basemvp.MainApplication;
import com.example.pc.basemvp.R;
import com.example.pc.basemvp.data.source.remote.api.error.BaseException;
import com.example.pc.basemvp.repositories.UserRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MainApplication) getApplication()).getAppComponent().inject(this);

        mUserRepository.doLogin("sds", "sds", 1);

        Disposable disposable = mUserRepository.doLogin("", "", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Toast.makeText(HomeActivity.this, data.getData().getToken(), Toast.LENGTH_LONG).show();
                }, throwable -> {
                    if (throwable instanceof BaseException) {
                        Toast.makeText(HomeActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
