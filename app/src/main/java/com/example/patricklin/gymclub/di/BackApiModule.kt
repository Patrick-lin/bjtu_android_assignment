package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.BuildConfig
import com.example.patricklin.gymclub.model.ClassApi
import com.example.patricklin.gymclub.model.TrainerApi
import com.example.patricklin.gymclub.model.UserApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class BackApiModule {
    private val api: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_SERVER)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideUserApi(): UserApi = api.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideClassApi(): ClassApi = api.create(ClassApi::class.java)

    @Provides
    @Singleton
    fun provideTrainreApi(): TrainerApi = api.create(TrainerApi::class.java)
}