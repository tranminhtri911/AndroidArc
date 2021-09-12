package com.example.pc.basemvp.data.source.remote.service;

import com.example.pc.basemvp.BuildConfig;
import com.example.pc.basemvp.data.source.remote.api.middleware.BooleanAdapter;
import com.example.pc.basemvp.data.source.remote.api.middleware.IntegerAdapter;
import com.example.pc.basemvp.data.source.remote.api.middleware.InterceptorImpl;
import com.example.pc.basemvp.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory;
import com.example.pc.basemvp.utils.Constants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.data
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:52
 * -------------^_^-------------
 **/
public class RetrofitProvider {

    private static final String BASE_URL = BuildConfig.ENDPOINT;
    private Cache mCache;
    private Retrofit mRetrofit;
    private static RetrofitProvider sInstance;

    public static synchronized RetrofitProvider getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitProvider();
        }
        return sInstance;
    }

    private RetrofitProvider() {
        Gson gson = getConfigGson();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(getConfigOkHttpClient())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        mRetrofit = retrofitBuilder.build();
    }

    private Gson getConfigGson() {
        BooleanAdapter booleanAdapter = new BooleanAdapter();
        IntegerAdapter integerAdapter = new IntegerAdapter();
        return new GsonBuilder().registerTypeAdapter(Boolean.class, booleanAdapter)
                .registerTypeAdapter(boolean.class, booleanAdapter)
                .registerTypeAdapter(Integer.class, integerAdapter)
                .registerTypeAdapter(int.class, integerAdapter)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    private OkHttpClient getConfigOkHttpClient() {
        /* Config OkHttpClient */

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.cache(this.mCache);
        okHttpClient.addInterceptor(InterceptorImpl.getInstance());

        // ignore timeout exception in case response is big json string
        okHttpClient.connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            okHttpClient.addInterceptor(logging);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return okHttpClient.build();
    }

    public void setCache(Cache cache) {
        this.mCache = cache;
    }

    public <T> T makeApi(Class<T> tClass) {
        return this.mRetrofit.create(tClass);
    }
}
