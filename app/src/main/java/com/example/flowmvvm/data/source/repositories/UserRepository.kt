package com.example.flowmvvm.data.source.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.flowmvvm.base.paging.BasePagingDataAdapter
import com.example.flowmvvm.base.paging.NetworkState
import com.example.flowmvvm.data.model.User
import com.example.flowmvvm.data.source.dataSource.UserDataSource
import com.example.flowmvvm.data.source.local.sharedprf.SharedPrefsApi
import com.example.flowmvvm.data.source.local.sharedprf.SharedPrefsKey
import com.example.flowmvvm.data.source.remote.service.ApiService
import com.example.flowmvvm.utils.dispatchers.BaseDispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface UserRepository {

    fun searchRepository(query: String, page: Int): Flow<List<User>>

    fun saveUserToLocal(user: User)

    fun getUserFromLocal(): User?

    fun searchRepositoryPaging(
        query: String,
        networkState: MutableStateFlow<NetworkState<*>>
    ): Flow<PagingData<User>>
}

class UserRepositoryImpl
@Inject
constructor(
    private val dispatcherProvider: BaseDispatcherProvider,
    private val apiService: ApiService,
    private val sharedPrefsApi: SharedPrefsApi,
    private val gson: Gson
) : UserRepository, BaseRepository() {

    override fun searchRepository(query: String, page: Int): Flow<List<User>> {
        return safeApiCall { apiService.searchRepository(query, page).data }
            .flowOn(dispatcherProvider.io())
    }

    override fun saveUserToLocal(user: User) {
        val data = gson.toJson(user)
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, data)
    }

    override fun getUserFromLocal(): User? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_USER, User::class.java)
    }

    override fun searchRepositoryPaging(
        query: String,
        networkState: MutableStateFlow<NetworkState<*>>
    ): Flow<PagingData<User>> {
        return Pager(
            config = BasePagingDataAdapter.config,
            pagingSourceFactory = {
                UserDataSource(query, apiService).apply {
                    setNetworkState(networkState)
                }
            })
            .flow
    }
}
