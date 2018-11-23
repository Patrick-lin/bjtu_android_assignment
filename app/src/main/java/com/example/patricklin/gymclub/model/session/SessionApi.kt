package com.example.patricklin.gymclub.model.session

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface SessionApi {
    data class GetSessionsResult(
            val list: List<Session>,
            val error: String?
    )
    @GET("/classes")
    fun getSessions(@Header("authorization") token: String): Deferred<GetSessionsResult>
}