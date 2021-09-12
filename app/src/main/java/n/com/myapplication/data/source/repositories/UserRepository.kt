package n.com.myapplication.data.source.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import n.com.myapplication.data.model.User
import n.com.myapplication.data.source.local.sharedprf.SharedPrefsApi
import n.com.myapplication.data.source.local.sharedprf.SharedPrefsKey
import n.com.myapplication.data.source.remote.service.AppApi
import n.com.myapplication.util.Constant
import n.com.myapplication.util.liveData.NetWorkState

interface UserRepository {

    fun saveUserToLocal(user: User)

    fun getUserFromLocal(): User?

    fun searchRepository(query: String?, page: Int): Single<MutableList<User>>

}

class UserRepositoryImpl
constructor(private val api: AppApi, private val sharedPrefsApi: SharedPrefsApi) : UserRepository {

    private val gson = Gson()

    val user = User()

    override fun saveUserToLocal(user: User) {
        val data = gson.toJson(user)
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, data)
    }

    override fun getUserFromLocal(): User? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_USER, User::class.java)
    }

    override fun searchRepository(query: String?, page: Int): Single<MutableList<User>> {
        return api.searchRepository(query, page).map { value -> value.items }
    }

}

class UserDataSourceFactory(
        private val query: String,
        private val compositeDisposable: CompositeDisposable,
        private val networkService: UserRepository)
    : DataSource.Factory<Int, User>() {

    val dataSource = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        val newsDataSource = UserDataSource(query, networkService, compositeDisposable)
        dataSource.postValue(newsDataSource)
        return newsDataSource
    }
}

class UserDataSource(
        private val query: String,
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, User>() {

    private var retryCompletable: Completable? = null

    var netWorkState: MutableLiveData<NetWorkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, User>) {
        updateState(NetWorkState.FETCH)
        val disposable = userRepository.searchRepository(query, Constant.PAGE_DEFAULT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            updateState(NetWorkState.SUCCESS)
                            callback.onResult(it, null, 2)
                        },
                        onError = {
                            updateState(NetWorkState.ERROR)
                            setRetry(Action { loadInitial(params, callback) })
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        updateState(NetWorkState.START_LOAD_MORE)
        val disposable = userRepository.searchRepository(query,  params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            updateState(NetWorkState.STOP_LOAD_MORE)
                            callback.onResult(it, params.key + 1)
                        },
                        onError = {
                            updateState(NetWorkState.ERROR)
                            setRetry(Action { loadAfter(params, callback) })
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
    }

    private fun updateState(state: NetWorkState) {
        netWorkState.postValue(state)
    }

    fun retry() {
        val source = retryCompletable ?: return
        val disposable = source
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}
