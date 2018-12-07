package com.example.patricklin.gymclub.model.trainer

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "trainers")
data class Trainer(
        @field:Json(name = "_id")
        @PrimaryKey
        val id: String,

        val firstName: String,
        val lastName: String,
        val age: Int,
        val cover: String? = null
) {
    val fullName: String get() = "$lastName $firstName"
}