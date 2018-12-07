package com.example.patricklin.gymclub.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.patricklin.gymclub.model.trainer.Trainer

@Database(
        entities = [Trainer::class],
        version = 1,
        exportSchema = false
)
abstract class GymDatabase : RoomDatabase() {
    abstract fun gymDao(): GymDao
}