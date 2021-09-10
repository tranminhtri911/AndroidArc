package com.example.mvvmkoin.screen.main.userFavorite

import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.launch
import com.example.mvvmkoin.base.BaseViewModel
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.data.source.repositories.AppDBRepository
import com.example.mvvmkoin.util.LogUtils

class UserFavoriteViewModel
constructor(private val appDB: AppDBRepository) : BaseViewModel() {

    var repoList = MediatorLiveData<MutableList<User>>()

    init {
        viewModelScope.launch {
            repoList.addSource(appDB.getUsers()) { response ->
                LogUtils.d(UserFavoriteFragment.TAG, "launch")
                repoList.value = response
            }
        }
    }

    fun unFavorite(user: User) {
        viewModelScope.launch {
            appDB.deleteUser(user)
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}