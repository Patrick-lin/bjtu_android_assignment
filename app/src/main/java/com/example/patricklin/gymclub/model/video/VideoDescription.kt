package com.example.patricklin.gymclub.model.video

import com.example.patricklin.gymclub.BuildConfig

data class VideoDescription(
        val id: String,
        val title: String,
        val thumbnail: String?
) {
    val streamUrl: String
    get() = "${BuildConfig.URL_SERVER}/video/stream/$id"
}
