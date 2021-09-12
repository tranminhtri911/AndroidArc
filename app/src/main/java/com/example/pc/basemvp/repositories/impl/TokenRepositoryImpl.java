package com.example.pc.basemvp.repositories.impl;

import com.example.pc.basemvp.data.source.local.sharePrf.SharedPrefsApi;
import com.example.pc.basemvp.data.source.local.sharePrf.SharedPrefsKey;
import com.example.pc.basemvp.repositories.TokenRepository;

public class TokenRepositoryImpl implements TokenRepository {
    private SharedPrefsApi mSharedPrefsApi;

    public TokenRepositoryImpl(SharedPrefsApi sharedPrefsApi) {
        this.mSharedPrefsApi = sharedPrefsApi;
    }

    @Override
    public void saveToken(String token) {
        mSharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, token);
    }

    @Override
    public String getToken() {
        return mSharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, String.class);
    }

    @Override
    public void clearToken() {
        mSharedPrefsApi.clearKey(SharedPrefsKey.KEY_TOKEN);
    }
}
