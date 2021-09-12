package com.example.pc.basemvp;

import android.content.Context;
import com.example.pc.basemvp.data.source.local.sharePrf.SharedPrefsApi;
import com.example.pc.basemvp.data.source.local.sharePrf.SharedPrefsImpl;
import com.example.pc.basemvp.data.source.remote.service.RetrofitProvider;
import com.example.pc.basemvp.utils.Constants;
import com.example.pc.basemvp.utils.scheduler.BaseSchedulerProvider;
import com.example.pc.basemvp.utils.scheduler.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.Cache;

@Module
public class ApplicationModule {
    private Context mContext;

    ApplicationModule(Context context) {
        this.mContext = context;
        Cache cache =
                new Cache(context.getApplicationContext().getCacheDir(), Constants.CACHE_SIZE);
        RetrofitProvider.getInstance().setCache(cache);
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public SharedPrefsApi provideSharedPrefsApi() {
        return new SharedPrefsImpl(mContext);
    }

    @Provides
    @Singleton
    public BaseSchedulerProvider provideBaseSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
