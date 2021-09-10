package com.example.mvvmkoin.screen.main.user

import androidx.lifecycle.MutableLiveData
import com.example.mvvmkoin.base.BaseViewModel
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.data.source.remote.api.error.RetrofitException
import com.example.mvvmkoin.data.source.repositories.AppDBRepository
import com.example.mvvmkoin.data.source.repositories.UserRepository
import com.example.mvvmkoin.util.Constant
import com.example.mvvmkoin.util.extension.withScheduler
import com.example.mvvmkoin.util.liveData.NetWorkState
import com.example.mvvmkoin.util.liveData.Resource
import com.example.mvvmkoin.util.liveData.SingleLiveEvent
import com.example.mvvmkoin.util.rxAndroid.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class UserViewModel
constructor(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val userRepository: UserRepository,
    private val appDb: AppDBRepository
) : BaseViewModel() {

    var query = MutableLiveData<String>().apply { value = "cat" }
    var repoList = SingleLiveEvent<Resource<MutableList<User>>>()
    var isSearching = MutableLiveData<Boolean>()

    fun searchUser(netWorkState: NetWorkState, page: Int = Constant.PAGE_DEFAULT) {
        launchDisposable {
            userRepository.searchRepository(query.value, page)
                .withScheduler(baseSchedulerProvider)
                .subscribeBy(
                    onSuccess = {
                        repoList.value = Resource.multiStatus(netWorkState, it.toMutableList())
                    }
                    ,
                    onError = { error ->
                        if (error is RetrofitException) {
                            repoList.value = Resource.error(error)
                        }
                    })


        }
    }

    fun initRxSearch(searchObservable: Observable<String>) {
        launchDisposable {
            searchObservable
                .debounce(Constant.TIMEOUT_SEARCH, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { query ->
                    isSearching.postValue(true)
                    userRepository.searchRepository(query, Constant.PAGE_DEFAULT)
                        .toObservable()
                        .onErrorResumeNext(Observable.empty())
                }
                .withScheduler(baseSchedulerProvider)
                .subscribeBy(
                    onNext = {
                        isSearching.postValue(false)
                        repoList.value = Resource.refreshData(it.toMutableList())
                    },
                    onError = { error ->
                        isSearching.postValue(false)
                        if (error is RetrofitException) {
                            repoList.value = Resource.error(error)
                        }
                        initRxSearch(searchObservable)
                    })

        }
    }

    fun pressFavorite(user: User) {
        viewModelScope.launch {
            appDb.insertOrUpdateUser(user)
        }
    }

    override fun onCleared() {
        repoList.clear()
        viewModelJob.cancel()
        super.onCleared()
    }
}