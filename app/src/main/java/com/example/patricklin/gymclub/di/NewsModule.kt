package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.news.NewsApi
import com.example.patricklin.gymclub.model.news.NewsService
import com.example.patricklin.gymclub.model.news.NewsServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsModule {
    @Provides
    @Singleton
    fun provideNewsService(newsApi: NewsApi): NewsService = NewsServiceImpl(newsApi)
}
