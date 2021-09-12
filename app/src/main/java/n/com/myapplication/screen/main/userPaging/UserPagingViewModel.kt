package n.com.myapplication.screen.main.userPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import n.com.myapplication.base.BaseViewModel
import n.com.myapplication.data.model.User
import n.com.myapplication.data.source.repositories.UserDataSource
import n.com.myapplication.data.source.repositories.UserDataSourceFactory
import n.com.myapplication.data.source.repositories.UserRepository
import n.com.myapplication.util.liveData.NetWorkState
import javax.inject.Inject

class UserPagingViewModel
@Inject constructor(userRepository: UserRepository) : BaseViewModel() {

    var repoList: LiveData<PagedList<User>>

    private val pageSize = 5
    private val userDataSourceFactory = UserDataSourceFactory("cat", compositeDisposable,
            userRepository)

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        repoList = LivePagedListBuilder(userDataSourceFactory, config).build()

    }

    fun getState(): LiveData<NetWorkState> {
        val source = userDataSourceFactory.dataSource
        return Transformations.switchMap<UserDataSource, NetWorkState>(source, UserDataSource::netWorkState)
    }

    fun retry() {
        userDataSourceFactory.dataSource.value?.retry()
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}

