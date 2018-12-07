package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.patricklin.gymclub.model.trainer.Trainer

@Dao
interface GymDao {
    @Query("SELECT * FROM trainers")
    fun getTrainers(): LiveData<List<Trainer>>

    @Query("SELECT * FROM trainers WHERE id = :id")
    fun getTrainer(id: String): LiveData<Trainer>

    @Query("SELECT * FROM trainers WHERE id IN (:trainerIds)")
    fun getLiveTrainersIn(trainerIds: List<String>): LiveData<List<Trainer>>
    @Query("SELECT * FROM trainers WHERE id IN (:trainerIds)")
    fun getTrainersIn(trainerIds: List<String>): List<Trainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTrainers(trainers: List<Trainer>)
}