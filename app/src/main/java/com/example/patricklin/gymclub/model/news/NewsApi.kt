package com.example.patricklin.gymclub.model.news

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    data class GetNewsResult(val list: List<News>, val cursor: String?)

    @GET("/news")
    fun getNewsList(
            @Query("cursor") cursor: String? = null,
            @Query("limit") limit: Int? = null
    ): Deferred<GetNewsResult>
}