package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.github.javafaker.Faker

class ClassServiceImpl : ClassService {
    private val classes = MutableLiveData<List<Class>>()

    init {
        val faker = Faker()
        var counter = 0
        classes.value = listOf(
                Class(
                        id = counter++,
                        type = ClassType.PRIVATE_CLASS.toString(),
                        title = "Private training",
                        tagLine = faker.lorem().sentence(3),
                        cover = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg",
                        availableTrainerIds = listOf(0, 1, 2, 3, 4),
                        choosableTrainer = true,
                        nbChoosableTrainer = 1,
                        maxPlaces = 1,
                        takenPlaces = 0
                ),
                Class(
                        id = counter++,
                        type = ClassType.GROUP_CLASS.toString(),
                        title = "Cardio training",
                        tagLine = faker.lorem().sentence(3),
                        cover = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg",
                        availableTrainerIds = listOf(1),
                        maxPlaces = 50,
                        takenPlaces = 5
                ),
                Class(
                        id = counter++,
                        type = ClassType.GROUP_CLASS.toString(),
                        title = "Yoga training",
                        tagLine = faker.lorem().sentence(3),
                        cover = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg",
                        availableTrainerIds = listOf(2),
                        maxPlaces = 50,
                        takenPlaces = 12
                ),
                Class(
                        id = counter++,
                        type = ClassType.GROUP_CLASS.toString(),
                        title = "Dance training",
                        tagLine = faker.lorem().sentence(3),
                        cover = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg",
                        availableTrainerIds = listOf(0, 3),
                        maxPlaces = 50,
                        takenPlaces = 50
                )
        )
    }

    override fun getClasses(): LiveData<List<Class>> = classes

    override fun getLiveClass(id: Int): LiveData<Class> = Transformations.map(classes) {
        it.find { c -> c.id == id }
    }

    override fun getClass(id: Int): Class? = classes.value.orEmpty().find { c -> c.id == id }
}