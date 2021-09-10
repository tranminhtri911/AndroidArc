package com.example.flowmvvm.di

import android.app.Application
import android.content.res.Resources
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flowmvvm.data.source.local.dao.AppDatabase
import com.example.flowmvvm.data.source.local.sharedprf.SharedPrefsApi
import com.example.flowmvvm.data.source.local.sharedprf.SharedPrefsImpl
import com.example.flowmvvm.data.source.remote.api.middleware.BooleanAdapter
import com.example.flowmvvm.data.source.remote.api.middleware.DoubleAdapter
import com.example.flowmvvm.data.source.remote.api.middleware.IntegerAdapter
import com.example.flowmvvm.di.AppModuleCons.DB_NAME
import com.example.flowmvvm.utils.DateTimeUtils
import com.example.flowmvvm.utils.dispatchers.BaseDispatcherProvider
import com.example.flowmvvm.utils.dispatchers.DispatcherProvider
import com.example.flowmvvm.utils.rxAndroid.RxBaseSchedulerProvider
import com.example.flowmvvm.utils.rxAndroid.RxSchedulerProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideResources(app: Application): Resources {
        return app.resources
    }

    @Singleton
    @Provides
    fun provideDispatcher(): BaseDispatcherProvider {
        return DispatcherProvider()
    }

    @Singleton
    @Provides
    fun provideBaseSchedulerProvider(): RxBaseSchedulerProvider {
        return RxSchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideSharedPrefsApi(app: Application): SharedPrefsApi {
        return SharedPrefsImpl(app)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app.applicationContext, AppDatabase::class.java, DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val booleanAdapter = BooleanAdapter()
        val integerAdapter = IntegerAdapter()
        val doubleAdapter = DoubleAdapter()
        return GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, booleanAdapter)
            .registerTypeAdapter(Int::class.java, integerAdapter)
            .registerTypeAdapter(Double::class.java, doubleAdapter)
            .setDateFormat(DateTimeUtils.DATE_TIME_FORMAT_UTC)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
}

object AppModuleCons {
    const val DB_NAME = "db_name"

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //No-Op
        }
    }
}