package com.example.pc.basemvp.screen.login;

import com.example.pc.basemvp.base.BasePresenter;
import com.example.pc.basemvp.base.BaseView;
import com.example.pc.basemvp.data.model.User;
import com.example.pc.basemvp.data.source.remote.api.error.BaseException;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LoginContract {
    /**
     * View.
     */
    interface View extends BaseView {

        void onGetUserSuccess(User user);

        void onGetError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void doLogin(String email, String password, int type);
    }
}
