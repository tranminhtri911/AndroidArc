package com.example.flowmvvm.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope