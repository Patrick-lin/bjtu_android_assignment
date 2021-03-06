package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.AuthServiceImpl
import com.example.patricklin.gymclub.model.ShopDao
import com.example.patricklin.gymclub.model.user.UserApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {
    @Provides
    @Singleton
    fun provideAuth(userApi: UserApi, shopDao: ShopDao): AuthService = AuthServiceImpl(userApi, shopDao)
}
