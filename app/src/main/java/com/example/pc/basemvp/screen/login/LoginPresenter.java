package com.example.pc.basemvp.screen.login;

import com.example.pc.basemvp.data.source.remote.api.error.BaseException;
import com.example.pc.basemvp.repositories.TokenRepository;
import com.example.pc.basemvp.repositories.UserRepository;
import com.example.pc.basemvp.utils.scheduler.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();

    private final LoginContract.View mView;
    private UserRepository mUserRepository;
    private TokenRepository mTokenRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginContract.View view, UserRepository userRepository,
            TokenRepository tokenRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.mView = view;
        this.mUserRepository = userRepository;
        this.mTokenRepository = tokenRepository;
        this.mBaseSchedulerProvider = baseSchedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void doLogin(String email, String password, int type) {
        this.mView.showLoading();
        Disposable disposable =
                mUserRepository.doLogin(email, password, type)
                        .flatMap(dataResponse -> {
                            mTokenRepository.saveToken(dataResponse.getData().getToken());
                            return mUserRepository.getUser();
                        })
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(userResponse -> {
                            this.mView.hideLoading();
                            if (userResponse == null || userResponse.getData() == null) {
                                return;
                            }
                            mView.onGetUserSuccess(userResponse.getData());
                        }, throwable -> {
                            this.mView.hideLoading();
                            if (throwable instanceof BaseException) {
                                mView.onGetError((BaseException) throwable);
                            }
                        });
        mCompositeDisposable.add(disposable);
    }
}
