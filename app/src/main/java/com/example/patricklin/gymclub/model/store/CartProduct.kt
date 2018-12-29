package com.example.patricklin.gymclub.model.store

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "cartProduct")
data class CartProduct(
        @PrimaryKey
        val _id: String,
        @ForeignKey(entity = Product::class, parentColumns = ["_id"], childColumns = ["productId"])
        val name: String,
        val storeId: String,
        val productId: String,
        val qty: Int,
        val total: Float,
        val cover: String?
)