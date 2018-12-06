package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.video.VideoApi
import com.example.patricklin.gymclub.model.video.VideoService
import com.example.patricklin.gymclub.model.video.VideoServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class VideoModule {
    @Provides
    @Singleton
    fun provideVideoService(videoApi: VideoApi): VideoService = VideoServiceImpl(videoApi)
}