package com.example.patricklin.gymclub.model.video

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface VideoApi {
    data class VideosRes(
        val list: List<VideoDescription>
    )

    @GET("/videos")
    fun getVideos(): Deferred<VideosRes>
}