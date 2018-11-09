package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.NewsService
import com.example.patricklin.gymclub.model.NewsServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsModule {
    @Provides
    @Singleton
    fun provideNewsService(): NewsService = NewsServiceImpl()
}
