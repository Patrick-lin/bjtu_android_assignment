package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ClassModule {
    @Provides
    @Singleton
    fun provideClassService(classApi: ClassApi, authService: AuthService): ClassService = ClassServiceImpl(classApi, authService)
}
