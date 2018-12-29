package com.example.patricklin.gymclub.model.news

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "news")
data class News(
        @field:Json(name = "_id")
        @PrimaryKey
        val id: String,
        val title: String,
        val text: String,
        val date: Date? = null,
        val tagLine: String? = null,
        val cover: String? = null
)