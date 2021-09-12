package n.com.myapplication.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import n.com.myapplication.di.AppViewModelFactory
import n.com.myapplication.di.ViewModelKey
import n.com.myapplication.screen.main.MainActivityViewModel
import n.com.myapplication.screen.main.user.UserViewModel
import n.com.myapplication.screen.userDetail.UserDetailViewModel
import n.com.myapplication.screen.main.userFavorite.UserFavoriteViewModel
import n.com.myapplication.screen.main.userPaging.UserPagingViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserPagingViewModel::class)
    abstract fun bindUserPagingViewModel(userPagingViewModel: UserPagingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserFavoriteViewModel::class)
    abstract fun bindUserFavoriteViewModel(userFavoriteViewModel: UserFavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    abstract fun bindUserDetailViewModel(userDetailViewModel: UserDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
