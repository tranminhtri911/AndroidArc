package com.example.mvvmkoin.data.source.dataSource

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mvvmkoin.base.paging.NetworkState
import com.example.mvvmkoin.util.LogUtils
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * BaseDataSourceFactory
 *
 * @param <T> is Object
 *
 */

abstract class BaseDataSource<T>
constructor(private val disposables: CompositeDisposable) : PageKeyedDataSource<Int, T>() {

    private var retryCompletable: Completable? = null

    var netWorkState: MutableLiveData<NetworkState> = MutableLiveData()

    var maxPage = 0

    private lateinit var runnable: Runnable

    private var handler: Handler? = Handler(Looper.getMainLooper())

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        updateState(NetworkState.LOADING)
        val disposable = initialSource()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    setRetry(null)
                    updateState(NetworkState.LOADED)
                    callback.onResult(it, null, 2)
                },
                onError = {
                    updateState(NetworkState.toError(it))
                    setRetry(Action { loadInitial(params, callback) })
                }
            )
        disposables.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val nextPage = params.key

        if (maxPage in 1 until nextPage) return

        updateState(NetworkState.LOADING)

        val disposable = loadMoreSource(nextPage = nextPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    setRetry(null)

                    callback.onResult(it, params.key + 1)

                    runnable = Runnable { updateState(NetworkState.LOADED) }
                    handler?.postDelayed(runnable, 100)

                },
                onError = {
                    updateState(NetworkState.toError(it))
                    setRetry(Action { loadAfter(params, callback) })
                }
            )
        disposables.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // No-Op
    }

    fun refresh() {
        maxPage = 0
        invalidate()
    }

    fun retry() {
        val source = retryCompletable ?: return
        val disposable = source
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        disposables.add(disposable)
    }

    fun clear() {
        handler?.removeCallbacks(runnable)
        handler = null
    }

    private fun updateState(state: NetworkState) {
        LogUtils.d(TAG, state.status.toString())
        netWorkState.postValue(state)
    }


    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }


    abstract fun initialSource(): Single<List<T>>

    abstract fun loadMoreSource(nextPage: Int): Single<List<T>>

    companion object {
        private const val TAG = "BaseDataSource"
    }
}