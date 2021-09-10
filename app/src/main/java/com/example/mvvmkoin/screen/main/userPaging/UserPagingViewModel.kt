package com.example.mvvmkoin.screen.main.userPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mvvmkoin.base.BaseViewModel
import com.example.mvvmkoin.base.paging.BasePageListAdapter
import com.example.mvvmkoin.base.paging.NetworkState
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.data.source.dataSource.UserDataSource
import com.example.mvvmkoin.data.source.dataSource.UserDataSourceFactory
import com.example.mvvmkoin.data.source.repositories.UserRepository

class UserPagingViewModel
constructor(userRepository: UserRepository) : BaseViewModel() {

    var repoList: LiveData<PagedList<User>>

    private val userDataSourceFactory =
        UserDataSourceFactory(
            "cat", compositeDisposable,
            userRepository
        )

    init {
        val config = BasePageListAdapter.config
        repoList = LivePagedListBuilder(userDataSourceFactory, config).build()

    }

    fun getState(): LiveData<NetworkState> {
        val source = userDataSourceFactory.dataSource
        return Transformations.switchMap<UserDataSource, NetworkState>(
            source,
            UserDataSource::netWorkState
        )
    }

    fun refresh() {
        userDataSourceFactory.refresh()
    }

    fun retry() {
        userDataSourceFactory.retry()
    }

    override fun onCleared() {
        userDataSourceFactory.clear()
        super.onCleared()
    }

}

