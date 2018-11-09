package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.GymClubApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: GymClubApp) {
    @Provides
    @Singleton
    fun provideApp() = app
}
