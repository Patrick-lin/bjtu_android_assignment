package com.example.patricklin.gymclub.model.trainer

import com.squareup.moshi.Json

data class Trainer(
        @field:Json(name = "_id") val id: String,
        val firstName: String,
        val lastName: String,
        val age: Int,
        val cover: String? = null
) {
    val fullName: String get() = "$lastName $firstName"
}