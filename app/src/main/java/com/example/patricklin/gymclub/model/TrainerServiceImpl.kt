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
        var counter = 0

        fun createTrainer() = Trainer(
                id = counter++,
                firstName = name.firstName(),
                lastName = name.lastName(),
                age = faker.number().numberBetween(20, 30),
                cover = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2013802756,3551706854&fm=26&gp=0.jpg"
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

    override fun getTrainersIn(ids: Iterable<Int>): LiveData<List<Trainer>> = Transformations.map(trainers) {
        it -> it.filter { trainer -> ids.find { id -> trainer.id == id } != null }
    }
    override fun getTrainersListIn(ids: Iterable<Int>): List<Trainer> = trainers.value?.filter {
        trainer -> ids.find { id -> trainer.id == id } != null
    } ?: emptyList()
}