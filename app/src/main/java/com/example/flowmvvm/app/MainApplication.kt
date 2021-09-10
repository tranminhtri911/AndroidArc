package com.example.flowmvvm.app

import android.app.Application
import com.example.flowmvvm.utils.GlideApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    override fun onLowMemory() {
        GlideApp.get(this).onLowMemory()
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        GlideApp.get(this).onTrimMemory(level)
        super.onTrimMemory(level)
    }

    companion object {
        lateinit var sInstance: MainApplication
    }
}