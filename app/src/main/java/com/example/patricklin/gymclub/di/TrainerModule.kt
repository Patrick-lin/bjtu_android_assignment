package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.ShopDao
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
            shopDao: ShopDao,
            trainerApi: TrainerApi,
            authService: AuthService
    ): TrainerService = TrainerServiceImpl(shopDao, trainerApi, authService)
}
