package com.example.mvvmkoin.util.extension

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import com.example.mvvmkoin.util.rxAndroid.BaseSchedulerProvider

/**
 * Use SchedulerProvider configuration for Observable
 */
fun Completable.withScheduler(scheduler: BaseSchedulerProvider): Completable =
        this.observeOn(scheduler.ui()).subscribeOn(scheduler.io())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.withScheduler(scheduler: BaseSchedulerProvider): Single<T> =
        this.observeOn(scheduler.ui()).subscribeOn(scheduler.io())

/**
 * Use SchedulerProvider configuration for Observable
 */
fun <T> Observable<T>.withScheduler(scheduler: BaseSchedulerProvider): Observable<T> =
        this.observeOn(scheduler.ui()).subscribeOn(scheduler.io())