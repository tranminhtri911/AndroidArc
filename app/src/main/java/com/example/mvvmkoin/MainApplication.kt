package com.example.mvvmkoin

import android.app.Application
import com.example.mvvmkoin.di.rootModule
import com.example.mvvmkoin.util.GlideApp
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            androidFileProperties()
            modules(rootModule)
        }

        configLeakCanary()

    }

    override fun onLowMemory() {
        GlideApp.get(this).onLowMemory()
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        GlideApp.get(this).onTrimMemory(level)
        super.onTrimMemory(level)
    }

    private fun configLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.install(this)
        }
    }

    companion object {
        lateinit var sInstance: MainApplication
    }

}