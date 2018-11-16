package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import com.example.patricklin.gymclub.core.UseCase

interface TrainerService {
    fun getTrainers(): LiveData<List<Trainer>>
    fun getTrainer(id: String): LiveData<Trainer>
    fun getTrainersIn(ids: Iterable<String>): LiveData<List<Trainer>>
    val getTrainersListIn: UseCase<List<Trainer>, List<String>>
}