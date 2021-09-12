package com.example.pc.basemvp.base;

import android.support.annotation.StringRes;
import android.view.View;
import com.example.pc.basemvp.utils.Constants;

public interface BaseView {

    /**
     * Show message dialog to give information
     * @param type
     * @param message
     */
    void showToast(Constants.ToastType type, String message);
    /**
     * Show alert dialog to get confirm
     * @param message message is show in dialog, String or {@link StringRes}
     * @param negativeLabel label of negative button, String or {@link StringRes}, pass null if don't want to show this button
     * @param negativeListener Callback of negative button, pass null if only dismiss dialog
     * @param positiveLabel label of negative button, String or {@link StringRes}, default is OK if don't pass anything
     * @param positiveListener Callback of positive button, pass null if only dismiss dialog
     */
    void showAlert(String title, String message,
            String negativeLabel, View.OnClickListener negativeListener,
            String positiveLabel, View.OnClickListener positiveListener);

    void showLoading();
    void hideLoading();
}
