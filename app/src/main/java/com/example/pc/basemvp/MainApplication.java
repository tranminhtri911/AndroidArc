package com.example.pc.basemvp;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import com.example.pc.basemvp.data.source.RepositoryModule;
import com.squareup.leakcanary.LeakCanary;

public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(getApplicationContext()))
                    .repositoryModule(new RepositoryModule())
                    .build();
        }
        return mAppComponent;
    }
}
