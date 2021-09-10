package com.example.mvvmkoin.data.source.remote.service

import com.example.mvvmkoin.data.model.User
import com.example.mvvmkoin.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface AppApi {

  @GET("/search/repositories")
  fun searchRepository(@Query("q") query: String?, @Query(
      "page") page: Int): Single<ApiResponse<List<User>>>

}