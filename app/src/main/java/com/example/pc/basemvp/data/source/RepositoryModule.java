package com.example.pc.basemvp.data.source;

import com.example.pc.basemvp.data.source.local.sharePrf.SharedPrefsApi;
import com.example.pc.basemvp.data.source.remote.api.middleware.InterceptorImpl;
import com.example.pc.basemvp.repositories.TokenRepository;
import com.example.pc.basemvp.repositories.UserRepository;
import com.example.pc.basemvp.repositories.impl.TokenRepositoryImpl;
import com.example.pc.basemvp.repositories.impl.UserRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public TokenRepository provideTokenRepository(SharedPrefsApi sharedPrefsApi) {
        return new TokenRepositoryImpl(sharedPrefsApi);
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository(TokenRepository tokenRepository) {
        InterceptorImpl.getInstance().setTokenRepository(tokenRepository);
        return new UserRepositoryImpl();
    }
}
