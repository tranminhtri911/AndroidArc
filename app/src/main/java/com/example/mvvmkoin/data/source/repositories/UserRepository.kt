package com.example.mvvmkoin.data.source.repositories

import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.data.source.local.sharedprf.SharedPrefsApi
import com.example.mvvmkoin.data.source.local.sharedprf.SharedPrefsKey
import com.example.mvvmkoin.data.source.remote.api.response.ApiResponse
import com.example.mvvmkoin.data.source.remote.service.AppApi
import com.google.gson.Gson
import io.reactivex.Single

interface UserRepository {

    fun saveUserToLocal(user: User)

    fun getUserFromLocal(): User?

    fun searchRepository(query: String?, page: Int): Single<List<User>>

    fun searchRepositoryWithPaging(query: String?, page: Int): Single<ApiResponse<List<User>>>

}

class UserRepositoryImpl
constructor(private val api: AppApi, private val sharedPrefsApi: SharedPrefsApi) :
    UserRepository {

    private val gson = Gson()

    val user = User()

    override fun saveUserToLocal(user: User) {
        val data = gson.toJson(user)
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, data)
    }

    override fun getUserFromLocal(): User? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_USER, User::class.java)
    }

    override fun searchRepository(query: String?, page: Int): Single<List<User>> {
        return api.searchRepository(query, page).map { value -> value.data }
    }

    override fun searchRepositoryWithPaging(
        query: String?,
        page: Int
    ): Single<ApiResponse<List<User>>> {
        return api.searchRepository(query, page)
    }

}
