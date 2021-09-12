package n.com.myapplication.screen.main.user

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.launch
import n.com.myapplication.base.BaseViewModel
import n.com.myapplication.data.model.User
import n.com.myapplication.data.source.remote.api.error.RetrofitException
import n.com.myapplication.data.source.repositories.AppDBRepository
import n.com.myapplication.data.source.repositories.UserRepository
import n.com.myapplication.util.Constant
import n.com.myapplication.util.extension.withScheduler
import n.com.myapplication.util.liveData.Resource
import n.com.myapplication.util.liveData.SingleLiveEvent
import n.com.myapplication.util.liveData.NetWorkState
import n.com.myapplication.util.rxAndroid.BaseSchedulerProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserViewModel
@Inject constructor(
        private val baseSchedulerProvider: BaseSchedulerProvider,
        private val userRepository: UserRepository,
        private val appDb: AppDBRepository) : BaseViewModel() {

    var query = MutableLiveData<String>().apply { value = "cat" }
    var repoList = SingleLiveEvent<Resource<MutableList<User>>>()
    var isSearching = MutableLiveData<Boolean>()

    fun searchUser(netWorkState: NetWorkState, page: Int = Constant.PAGE_DEFAULT) {
        launchDisposable {
            userRepository.searchRepository(query.value, page)
                    .withScheduler(baseSchedulerProvider)
                    .subscribeBy(
                            onSuccess = {
                                repoList.value = Resource.multiStatus(netWorkState, it)
                            }
                            ,
                            onError = { error ->
                                if (error is RetrofitException) {
                                    repoList.value = Resource.error(error)
                                }
                            })


        }
    }

    fun initRxSearch(searchObservable: Observable<String>) {
        launchDisposable {
            searchObservable
                    .debounce(Constant.TIMEOUT_SEARCH, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .switchMap { query ->
                        isSearching.postValue(true)
                        userRepository.searchRepository(query, Constant.PAGE_DEFAULT)
                                .toObservable()
                                .onErrorResumeNext(Observable.empty())
                    }
                    .withScheduler(baseSchedulerProvider)
                    .subscribeBy(
                            onNext = {
                                isSearching.postValue(false)
                                repoList.value = Resource.refreshData(it)
                            },
                            onError = { error ->
                                isSearching.postValue(false)
                                if (error is RetrofitException) {
                                    repoList.value = Resource.error(error)
                                }
                                initRxSearch(searchObservable)
                            })

        }
    }

    fun pressFavorite(user: User) {
        viewModelScope.launch {
            appDb.insertOrUpdateUser(user)
        }
    }

    override fun onCleared() {
        repoList.clear()
        viewModelJob.cancel()
        super.onCleared()
    }
}