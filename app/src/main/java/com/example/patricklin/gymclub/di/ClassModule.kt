package com.example.patricklin.gymclub.di

import com.example.patricklin.gymclub.model.*
import com.example.patricklin.gymclub.model.session.ClassApi
import com.example.patricklin.gymclub.model.session.ClassService
import com.example.patricklin.gymclub.model.session.ClassServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ClassModule {
    @Provides
    @Singleton
    fun provideClassService(classApi: ClassApi, authService: AuthService): ClassService = ClassServiceImpl(classApi, authService)
}
