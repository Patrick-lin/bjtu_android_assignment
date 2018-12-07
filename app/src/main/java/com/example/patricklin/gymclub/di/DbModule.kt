package com.example.patricklin.gymclub.di

import android.arch.persistence.room.Room
import com.example.patricklin.gymclub.GymClubApp
import com.example.patricklin.gymclub.model.GymDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(private val app: GymClubApp) {
    @Provides
    @Singleton
    fun provideDatabase() =
            Room.databaseBuilder(app.applicationContext, GymDatabase::class.java, "gymDb")
                    .fallbackToDestructiveMigration()
                    .build()
    @Provides
    @Singleton
    fun provideDao(db: GymDatabase) = db.gymDao()
}