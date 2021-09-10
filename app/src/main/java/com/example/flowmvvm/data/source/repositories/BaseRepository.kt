package com.example.flowmvvm.data.source.repositories

import com.example.flowmvvm.data.source.remote.api.response.ApiResponse
import com.example.flowmvvm.utils.LogUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRepository {

    protected fun <T> safeApiCall(requestBlock: suspend () -> T?): Flow<T> {
        return flow {
            try {
                val response = processData(requestBlock())
                emit(response)
            } catch (e: Exception) {
                LogUtils.e(TAG, e.message.toString())
                throw e
            }
        }
    }

    private fun <T> processData(data: T?): T {
        if (data == null) {
            throw Throwable("data is Null")
        } else {
            if (data is ApiResponse<*>) {
                if (data.data == null) {
                    throw Throwable("response.data is Null")
                }
            }
        }
        return data
    }

    companion object {
        private const val TAG = "API CALL"
    }
}