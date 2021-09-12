package com.example.pc.basemvp;

import com.example.pc.basemvp.data.source.RepositoryModule;
import com.example.pc.basemvp.screen.home.HomeActivity;
import com.example.pc.basemvp.screen.login.LoginActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ApplicationModule.class, RepositoryModule.class })
public interface AppComponent {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);
}
