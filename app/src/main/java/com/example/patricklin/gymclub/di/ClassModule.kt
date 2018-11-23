package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.*
import com.example.patricklin.gymclub.model.session.SessionApi
import com.example.patricklin.gymclub.model.session.SessionService
import com.example.patricklin.gymclub.model.session.SessionServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ClassModule {
    @Provides
    @Singleton
    fun provideClassService(sessionApi: SessionApi, authService: AuthService): SessionService = SessionServiceImpl(sessionApi, authService)
}
