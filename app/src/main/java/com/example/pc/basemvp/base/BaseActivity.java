package com.example.pc.basemvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.pc.basemvp.R;
import com.example.pc.basemvp.utils.Constants;
import com.example.pc.basemvp.widget.ConfirmDialog;
import com.example.pc.basemvp.widget.DLoading;
import es.dmoral.toasty.Toasty;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    /**
     * The constant TIME_OUT_FOR_SHOW_LOADING.
     */
    public static final int TIME_OUT_FOR_SHOW_LOADING = 30000;
    private DLoading mLoading;
    protected Handler mOsHandler;
    private final Object mLockObj = new Object();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initializing loading
        this.mLoading = new DLoading(this);
        this.mOsHandler = new Handler();

        // activity life cycle
        bundleData(savedInstanceState);
        initRootView(savedInstanceState);
        loadData(savedInstanceState);
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    @Override
    protected void onDestroy() {
        this.mOsHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void showToast(Constants.ToastType type, String message) {
        switch (type.toString()) {
            case Constants.ToastType.SUCCESS:
                Toasty.success(this, message).show();
                break;
            case Constants.ToastType.INFO:
                Toasty.info(this, message).show();
                break;
            case Constants.ToastType.WARNING:
                Toasty.warning(this, message).show();
                break;
            case Constants.ToastType.ERROR:
                Toasty.error(this, message).show();
                break;
        }
    }

    @Override
    public void showAlert(String title, String message, String negativeLabel,
            View.OnClickListener negativeListener, String positiveLabel,
            View.OnClickListener positiveListener) {
        if (message == null) {
            return;
        }

        ConfirmDialog dialog = new ConfirmDialog(this).setTitle(title)
                .setContent(message)
                .setNegativeLabel(negativeLabel)
                .setNegativeListener(negativeListener)
                .setPositiveLabel(positiveLabel)
                .setPositiveListener(positiveListener);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    protected Runnable mDismissLoadingTask = this::hideLoading;

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            synchronized (mLockObj) {
                showALoading();
                //Try dismiss loading after a time period for prevent loading mToastDialog is
                // shown persistent.
                mOsHandler.removeCallbacks(mDismissLoadingTask);
                mOsHandler.postDelayed(mDismissLoadingTask, TIME_OUT_FOR_SHOW_LOADING);
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            synchronized (mLockObj) {
                hideALoading();
            }
        });
    }

    private void showALoading() {
        if (isFinishing()) {
            return;
        }

        if (!this.mLoading.isShowing()) this.mLoading.show();
    }

    private void hideALoading() {
        if (isFinishing()) {
            return;
        }

        if (this.mLoading.isShowing()) this.mLoading.dismiss();
    }

    protected abstract void bundleData(Bundle savedInstanceState);

    protected abstract void initRootView(Bundle savedInstanceState);

    protected abstract void loadData(Bundle savedInstanceState);

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.transition.slide_from_right, R.transition.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.transition.slide_from_left, R.transition.slide_to_right);
    }
}
