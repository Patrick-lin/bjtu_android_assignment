package com.example.patricklin.gymclub.model

enum class ClassType {
    PRIVATE_CLASS,
    GROUP_CLASS
}

data class Class(
        val id: Int,
        val type: String,
        val title: String,
        val tagLine: String,

        val cover: String? = null,

        val maxPlaces: Int = 0,
        val takenPlaces: Int = 0,

        val availableTrainerIds: List<Int> = emptyList(),
        val choosableTrainer: Boolean = false,
        val nbChoosableTrainer: Int = 0
) {
    fun isFull() = takenPlaces >= maxPlaces
}

