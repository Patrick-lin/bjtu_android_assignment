package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.GymDao
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
            gymDao: GymDao,
            trainerApi: TrainerApi,
            authService: AuthService
    ): TrainerService = TrainerServiceImpl(gymDao, trainerApi, authService)
}
