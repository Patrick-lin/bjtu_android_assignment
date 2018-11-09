package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData

interface TrainerService {
    fun getTrainers(): LiveData<List<Trainer>>
    fun getTrainer(id: Int): LiveData<Trainer>
}