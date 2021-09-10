package com.example.mvvmkoin.di

import android.app.Application
import com.example.mvvmkoin.data.source.local.dao.AppDatabase
import com.example.mvvmkoin.data.source.local.sharedprf.SharedPrefsApi
import com.example.mvvmkoin.data.source.local.sharedprf.SharedPrefsImpl
import com.example.mvvmkoin.data.source.remote.service.AppApi
import com.example.mvvmkoin.data.source.repositories.*
import com.example.mvvmkoin.data.source.repositories.UserRepository
import com.example.mvvmkoin.data.source.repositories.UserRepositoryImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val RepositoryModule = module {

    single { provideTokenRepository(androidApplication()) }

    single { provideUserRepository(get(), get()) }

    single { provideAppDBRepository(get(), get()) }
}

fun provideTokenRepository(app: Application): TokenRepository {
    return TokenRepository(SharedPrefsImpl(app))
}


fun provideUserRepository(api: AppApi, sharedPrefsApi: SharedPrefsApi): UserRepository {
    return UserRepositoryImpl(
        api,
        sharedPrefsApi
    )
}


fun provideAppDBRepository(appDatabase: AppDatabase, gson: Gson): AppDBRepository {
    return AppDBRepositoryImpl(appDatabase, gson)
}