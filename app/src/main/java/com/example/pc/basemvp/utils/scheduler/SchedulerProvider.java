package com.example.pc.basemvp.utils.scheduler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SchedulerProvider implements BaseSchedulerProvider {
    @Nullable
    private static SchedulerProvider mInstance;

    // Prevent direct instantiation.
    private SchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (mInstance == null) {
            mInstance = new SchedulerProvider();
        }
        return mInstance;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
