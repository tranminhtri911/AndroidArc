package n.com.myapplication.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import n.com.myapplication.screen.main.userFavorite.UserFavoriteFragment
import n.com.myapplication.screen.main.user.UserFragment
import n.com.myapplication.screen.main.userPaging.UserPagingFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

  @ContributesAndroidInjector
  abstract fun contributeUserFragment(): UserFragment

  @ContributesAndroidInjector
  abstract fun contributeUserPagingFragment(): UserPagingFragment

  @ContributesAndroidInjector
  abstract fun contributeUserFavoriteFragment(): UserFavoriteFragment

}
