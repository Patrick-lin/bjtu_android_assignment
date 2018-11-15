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
                cover = avatars.random()
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


    companion object {
        val avatars = listOf(
                "https://www.randomaddressgenerator.com/media/face/male36.jpg",
                "https://www.randomaddressgenerator.com/media/face/male67.jpg",
                "https://www.randomaddressgenerator.com/media/face/female72.jpg",
                "https://www.randomaddressgenerator.com/media/face/male44.jpg",
                "https://www.randomaddressgenerator.com/media/face/female40.jpg",
                "https://www.randomaddressgenerator.com/media/face/male50.jpg",
                "https://www.randomaddressgenerator.com/media/face/male32.jpg",
                "https://www.randomaddressgenerator.com/media/face/female82.jpg"
        )
    }
}