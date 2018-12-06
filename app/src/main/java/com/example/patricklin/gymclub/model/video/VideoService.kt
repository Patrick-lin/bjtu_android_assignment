package com.example.patricklin.gymclub.model.video

import android.arch.lifecycle.LiveData

interface VideoService {
    fun getVideoList(): LiveData<List<VideoDescription>>
    fun getVideo(id: String): VideoDescription?
}