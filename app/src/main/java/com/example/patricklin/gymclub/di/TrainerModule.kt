package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.trainer.TrainerApi
import com.example.patricklin.gymclub.model.trainer.TrainerService
import com.example.patricklin.gymclub.model.trainer.TrainerServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TrainerModule {
    @Provides
    @Singleton
    fun provideTrainerService(
            trainerApi: TrainerApi,
            authService: AuthService
    ): TrainerService = TrainerServiceImpl(trainerApi, authService)
}
