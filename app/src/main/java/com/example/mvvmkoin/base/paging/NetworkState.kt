package com.example.mvvmkoin.base.paging

enum class Status {
    LOADING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val error: Throwable? = null
) {
    companion object {

        val LOADING = NetworkState(Status.LOADING)

        val LOADED = NetworkState(Status.SUCCESS)

        fun toError(error: Throwable?) = NetworkState(Status.FAILED, error)
    }
}