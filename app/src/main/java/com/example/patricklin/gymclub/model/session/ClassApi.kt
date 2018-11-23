package com.example.patricklin.gymclub.model.session

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface ClassApi {
    data class GetClassResult(
            val list: List<Class>,
            val error: String?
    )
    @GET("/classes")
    fun getClasses(@Header("authorization") token: String): Deferred<GetClassResult>
}