package com.example.flowmvvm.di

import android.app.Application
import com.example.flowmvvm.data.source.local.dao.AppDatabase
import com.example.flowmvvm.data.source.local.sharedprf.SharedPrefsApi
import com.example.flowmvvm.data.source.local.sharedprf.SharedPrefsImpl
import com.example.flowmvvm.data.source.remote.service.ApiService
import com.example.flowmvvm.data.source.repositories.*
import com.example.flowmvvm.utils.dispatchers.BaseDispatcherProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTokenRepository(app: Application): TokenRepository {
        return TokenRepository(SharedPrefsImpl(app))
    }

    @Singleton
    @Provides
    fun provideAppDBRepository(
        dispatcherProvider: BaseDispatcherProvider,
        appDatabase: AppDatabase,
        gson: Gson
    ): AppDBRepository {
        return AppDBRepositoryImpl(dispatcherProvider, appDatabase, gson)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        dispatcherProvider: BaseDispatcherProvider,
        apiService: ApiService,
        sharedPrefsApi: SharedPrefsApi,
        gson: Gson
    ): UserRepository {
        return UserRepositoryImpl(dispatcherProvider, apiService, sharedPrefsApi, gson)
    }
}

