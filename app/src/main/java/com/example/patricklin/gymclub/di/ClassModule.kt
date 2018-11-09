package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.ClassService
import com.example.patricklin.gymclub.model.ClassServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ClassModule {
    @Provides
    @Singleton
    fun provideClassService(): ClassService = ClassServiceImpl()
}
