package com.example.patricklin.gymclub.model.store

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey
    val id: String,
    val userId: String,
    val total: Float,

    @Ignore
    val products: List<CartProduct>
)