package com.example.patricklin.gymclub.model.store

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "store")
data class Store(
        @field:Json(name= "_id")
        @PrimaryKey
        val id: String,
        val name: String,
        val description: String,
        val cover: String? = null
)

