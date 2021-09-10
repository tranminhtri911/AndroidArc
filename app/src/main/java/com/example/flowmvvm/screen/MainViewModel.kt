package com.example.flowmvvm.screen

import androidx.lifecycle.viewModelScope
import com.example.flowmvvm.base.BaseViewModel
import com.example.flowmvvm.data.model.User
import com.example.flowmvvm.utils.LogUtils
import com.example.flowmvvm.utils.flows.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor() : BaseViewModel() {

    val testBehaviorRelay = BehaviorRelay<User>()

    init {
        viewModelScope.launch {
            testBehaviorRelay.subscribe {
                LogUtils.e("testBehaviorRelay", it.fullName.toString())
            }
        }
    }
}

