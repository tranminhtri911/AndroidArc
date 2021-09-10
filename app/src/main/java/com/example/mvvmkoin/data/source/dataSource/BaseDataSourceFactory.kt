package com.example.mvvmkoin.data.source.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


/**
 * BaseDataSourceFactory
 *
 * @param <T> is Object
 * @param <DS> is new DataSource
 *
 */

abstract class BaseDataSourceFactory<T, DS : BaseDataSource<T>> : DataSource.Factory<Int, T>() {

    val dataSource = MutableLiveData<DS>()

    override fun create(): DataSource<Int, T> {
        val newDataSource = newDataSource
        dataSource.postValue(newDataSource)
        return newDataSource
    }

    fun refresh() {
        dataSource.value?.refresh()
    }

    fun retry() {
        dataSource.value?.retry()
    }

    fun clear() {
        dataSource.value?.clear()
    }

    protected abstract val newDataSource: DS

}
