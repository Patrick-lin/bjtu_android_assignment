package com.example.patricklin.gymclub.model.video

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class  VideoServiceImpl(val videoApi: VideoApi) : VideoService {
    val videos = MutableLiveData<List<VideoDescription>>()

    init {
        videos.value = emptyList()
    }

    override fun getVideoList(): LiveData<List<VideoDescription>> {
        GlobalScope.launch {
            try {
                val res = videoApi.getVideos().await()
                videos.postValue(res.list)
            } catch (err: Throwable) {
                Log.i("test", "$err")
            }
        }

        return videos
    }

    override fun getVideo(id: String): VideoDescription? {
        return videos.value?.find { it.id == id }
    }
}