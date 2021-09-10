package com.example.mvvmkoin.screen.main

import com.example.mvvmkoin.base.BaseViewModel
import com.example.mvvmkoin.util.liveData.SingleLiveEvent

class MainActivityViewModel : BaseViewModel() {

    var currentTab = MainActivity.TAB1

    var actionShowDrawer = SingleLiveEvent<Int>()
}