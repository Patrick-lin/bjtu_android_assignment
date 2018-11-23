package com.example.patricklin.gymclub.model.trainer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.example.patricklin.gymclub.core.Either
import com.example.patricklin.gymclub.core.Failure
import com.example.patricklin.gymclub.core.UseCase
import com.example.patricklin.gymclub.model.AuthService

import com.github.javafaker.Faker

class TrainerServiceImpl(private val trainerApi: TrainerApi, private val authService: AuthService) : TrainerService {
    private val trainers = MutableLiveData<List<Trainer>>()

    init {
        val faker = Faker()
        val name = faker.name()
        var counter = 0

        fun createTrainer() = Trainer(
                id = (counter++).toString(),
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

    override fun getTrainer(id: String): LiveData<Trainer> = Transformations.map(trainers) {
        it -> it.find { trainer -> trainer.id == id }
    }

    override fun getTrainersIn(ids: Iterable<String>): LiveData<List<Trainer>> = Transformations.map(trainers) {
        it -> it.filter { trainer -> ids.find { id -> trainer.id == id } != null }
    }

    override val getTrainersListIn = object : UseCase<List<Trainer>, List<String>>() {
        override suspend fun run(input: List<String>): Either<Failure, List<Trainer>> {
            try {
                val trainers = trainerApi.getTrainers(authService.getAuthHeader(), input).await()
                return Either.Right(trainers.list)
            } catch (err: Throwable) {
                Log.d("test", "$err")
                return Either.Left(Failure.detect(err))
            }

        }
    }

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