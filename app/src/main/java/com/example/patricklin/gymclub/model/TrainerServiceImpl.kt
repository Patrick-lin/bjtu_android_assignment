package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.github.javafaker.Faker

class TrainerServiceImpl : TrainerService {
    private val trainers = MutableLiveData<List<Trainer>>()

    init {
        val faker = Faker()
        val name = faker.name()
        val avatar = faker.avatar()
        var counter = 0

        fun createTrainer() = Trainer(
                id = counter++,
                firstName = name.firstName(),
                lastName = name.lastName(),
                cover = avatar.image()
        )


        trainers.value = listOf(
                createTrainer(),
                createTrainer(),
                createTrainer(),
                createTrainer(),
                createTrainer()
        )
    }

    override fun getTrainers(): LiveData<List<Trainer>> = trainers

    override fun getTrainer(id: Int): LiveData<Trainer> = Transformations.map(trainers) {
        it -> it.find { trainer -> trainer.id == id }
    }
}