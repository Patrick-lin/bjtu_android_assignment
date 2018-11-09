package com.example.patricklin.gymclub.model

data class Trainer(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val cover: String? = null
) {
    val fullName: String get() = "$lastName $firstName"
}