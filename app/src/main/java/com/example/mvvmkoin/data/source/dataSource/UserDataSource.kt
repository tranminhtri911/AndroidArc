package com.example.mvvmkoin.data.source.dataSource

import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.data.source.repositories.UserRepository
import com.example.mvvmkoin.util.Constant
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable


class UserDataSourceFactory
constructor(
    private val query: String,
    private val disposables: CompositeDisposable,
    private val userRepository: UserRepository
) : BaseDataSourceFactory<User, UserDataSource>() {

    override val newDataSource: UserDataSource
        get() = UserDataSource(query, userRepository, disposables)

}

class UserDataSource(
    private val query: String,
    private val userRepository: UserRepository,
    disposables: CompositeDisposable
) : BaseDataSource<User>(disposables) {

    override fun initialSource(): Single<List<User>> {
        return userRepository.searchRepositoryWithPaging(query, Constant.PAGE_DEFAULT)
            .map {
                maxPage = it.maxPage
                return@map it.data
            }
    }

    override fun loadMoreSource(nextPage: Int): Single<List<User>> {
        return userRepository.searchRepository(query, nextPage)
    }

}