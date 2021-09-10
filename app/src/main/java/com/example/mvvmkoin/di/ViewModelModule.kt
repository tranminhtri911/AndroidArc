package com.example.mvvmkoin.di

import com.example.mvvmkoin.screen.main.MainActivityViewModel
import com.example.mvvmkoin.screen.main.user.UserViewModel
import com.example.mvvmkoin.screen.main.userFavorite.UserFavoriteViewModel
import com.example.mvvmkoin.screen.main.userPaging.UserPagingViewModel
import com.example.mvvmkoin.screen.userDetail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val ViewModelModule: Module = module {

    viewModel { MainActivityViewModel() }

    viewModel { UserFavoriteViewModel(get()) }

    viewModel { UserViewModel(get(), get(), get()) }

    viewModel { UserPagingViewModel(get()) }

    viewModel { UserDetailViewModel() }

}