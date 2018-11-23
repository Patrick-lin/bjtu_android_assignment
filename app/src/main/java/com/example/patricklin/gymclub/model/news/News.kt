package com.example.patricklin.gymclub.model.news

import com.squareup.moshi.Json
import java.util.*

data class News(
        @field:Json(name = "_id") val id: String,
        val title: String,
        val text: String,
        val date: Date? = null,
        val tagLine: String? = null,
        val cover: String? = null
)