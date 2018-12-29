package com.example.patricklin.gymclub.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.patricklin.gymclub.model.store.CartProduct
import com.example.patricklin.gymclub.model.store.Product
import com.example.patricklin.gymclub.model.store.Store
import com.example.patricklin.gymclub.model.trainer.Trainer
import com.example.patricklin.gymclub.model.user.User

@Database(
        entities = [Trainer::class, User::class, Product::class, CartProduct::class, Store::class],
        version = 7,
        exportSchema = false
)
abstract class GymDatabase : RoomDatabase() {
    abstract fun gymDao(): ShopDao
}