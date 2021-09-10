package com.example.flowmvvm.screen.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.flowmvvm.base.BaseViewModel
import com.example.flowmvvm.data.model.User
import com.example.flowmvvm.data.source.repositories.AppDBRepository
import com.example.flowmvvm.utils.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel
@Inject
constructor(private val appDBRepository: AppDBRepository) : BaseViewModel() {

    val users: LiveData<List<User>> by lazy { appDBRepository.getUsers() }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            appDBRepository.deleteUser(user)
                .collectLatest {
                    LogUtils.d("Delete User Succeeded: ", user.fullName.toString())
                }
        }
    }
}