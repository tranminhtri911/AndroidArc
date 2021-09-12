package com.example.pc.basemvp.data.source.remote.api.middleware;

import android.support.annotation.NonNull;
import com.example.pc.basemvp.repositories.TokenRepository;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorImpl implements Interceptor {

    private static final String TAG = "InterceptorImpl";
    private static final String TOKEN_TYPE = "Bearer ";
    private static final String KEY_TOKEN = "Authorization";

    private static InterceptorImpl sInstance;

    private TokenRepository mTokenRepository;

    public static synchronized InterceptorImpl getInstance() {
        if (sInstance == null) {
            sInstance = new InterceptorImpl();
        }
        return sInstance;
    }

    public void setTokenRepository(TokenRepository tokenRepository) {
        mTokenRepository = tokenRepository;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //TODO check connection

        Request.Builder builder = initializeHeader(chain);
        Request request = builder.build();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (InterruptedIOException e) {
            response = chain.proceed(request);
        }

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            refreshToken();

            builder.removeHeader(KEY_TOKEN);

            String accessToken = this.mTokenRepository.getToken();
            if (accessToken != null && accessToken.isEmpty()) {
                builder.addHeader(KEY_TOKEN, TOKEN_TYPE + accessToken);
            }

            request = builder.build();
            response = chain.proceed(request);
        }
        return response;
    }

    private Request.Builder initializeHeader(Chain chain) {
        Request originRequest = chain.request();
        Request.Builder builder = originRequest.newBuilder()
                .header("Accept", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Cache-Control", "no-store")
                .method(originRequest.method(), originRequest.body());

        String accessToken = this.mTokenRepository.getToken();
        if (accessToken != null) {
            builder.addHeader(KEY_TOKEN, TOKEN_TYPE + accessToken);
        }
        return builder;
    }

    private void refreshToken() {
        //TODO Call Api refresh token
    }
}