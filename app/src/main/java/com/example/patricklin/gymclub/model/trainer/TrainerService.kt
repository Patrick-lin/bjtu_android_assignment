package com.example.patricklin.gymclub.model.trainer

import android.arch.lifecycle.LiveData
import com.example.patricklin.gymclub.core.UseCase
import com.example.patricklin.gymclub.model.trainer.Trainer

interface TrainerService {
    fun getTrainers(): LiveData<List<Trainer>>
    fun getTrainer(id: String): LiveData<Trainer>
    fun getTrainersIn(ids: List<String>): LiveData<List<Trainer>>
    val getTrainersListIn: UseCase<List<Trainer>, List<String>>
}