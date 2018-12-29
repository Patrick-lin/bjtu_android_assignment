package com.example.patricklin.gymclub.model.store

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "product")
data class Product (
        @PrimaryKey val _id: String,
        val name: String,
        val storeId: String,
        val description: String,
        val price: Float,
        val cover: String?
)