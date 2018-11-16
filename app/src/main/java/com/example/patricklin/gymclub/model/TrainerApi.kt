package com.example.patricklin.gymclub.model

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TrainerApi {
    data class GetTrainersResult(
            val list: List<Trainer>,
            val error: String?
    )
    @GET("/trainers")
    fun getTrainers(
            @Header("authorization") token: String,
            @Query("ids") selection: List<String>? = null
    ): Deferred<GetTrainersResult>
}