package com.example.patricklin.gymclub.model.news

import java.util.*

data class News(
        val id: Int,
        val title: String,
        val text: String,
        val date: Date,
        val tagLine: String? = null,
        val cover: String? = null
)