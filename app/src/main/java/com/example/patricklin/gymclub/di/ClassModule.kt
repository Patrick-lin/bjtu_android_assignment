package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.*
import com.example.patricklin.gymclub.model.store.StoreApi
import com.example.patricklin.gymclub.model.store.StoreService
import com.example.patricklin.gymclub.model.store.StoreServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ClassModule {
    @Provides
    @Singleton
    fun provideClassService(shopDao: ShopDao, storeApi: StoreApi, authService: AuthService): StoreService =
            StoreServiceImpl(shopDao, storeApi, authService)
}
