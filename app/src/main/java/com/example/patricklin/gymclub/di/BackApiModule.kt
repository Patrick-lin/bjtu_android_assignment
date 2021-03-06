package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.BuildConfig
import com.example.patricklin.gymclub.model.news.NewsApi
import com.example.patricklin.gymclub.model.store.StoreApi
import com.example.patricklin.gymclub.model.trainer.TrainerApi
import com.example.patricklin.gymclub.model.user.UserApi
import com.example.patricklin.gymclub.model.video.VideoApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.util.*


@Module
class BackApiModule {
    private val api: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_SERVER)
            .addConverterFactory(
                    MoshiConverterFactory.create(Moshi.Builder()
                            .add(Date::class.java, Rfc3339DateJsonAdapter())
                            .build())
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideUserApi(): UserApi = api.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideStoreApi(): StoreApi = api.create(StoreApi::class.java)

    @Provides
    @Singleton
    fun provideTrainerApi(): TrainerApi = api.create(TrainerApi::class.java)

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi = api.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideVideoApi(): VideoApi = api.create(VideoApi::class.java)
}