package com.example.mvvmkoin.screen.userDetail

import androidx.lifecycle.MutableLiveData
import com.example.mvvmkoin.base.BaseViewModel
import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.util.liveData.SingleLiveEvent

class UserDetailViewModel : BaseViewModel() {

    var user = MutableLiveData<User>()

    var actionBack = SingleLiveEvent<Int>()

    fun setUser(data: User) {
        user.value = data
    }

    fun onBackPressed() {
        actionBack.postValue(null)
    }
}