package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.patricklin.gymclub.model.news.News
import com.example.patricklin.gymclub.model.store.Cart
import com.example.patricklin.gymclub.model.store.CartProduct
import com.example.patricklin.gymclub.model.store.Product
import com.example.patricklin.gymclub.model.store.Store
import com.example.patricklin.gymclub.model.trainer.Trainer
import com.example.patricklin.gymclub.model.user.User

@Dao
interface ShopDao {
    @Query("SELECT * FROM user")
    fun getUser(): User?
    @Insert
    fun createUser(user: User)
    @Query("DELETE FROM user")
    fun deleteUser()

    @Query("SELECT * FROM trainers")
    fun getTrainers(): LiveData<List<Trainer>>

    @Query("SELECT * FROM trainers WHERE id = :id")
    fun getTrainer(id: String): LiveData<Trainer>

    @Query("SELECT * FROM trainers WHERE id IN (:trainerIds)")
    fun getLiveTrainersIn(trainerIds: List<String>): LiveData<List<Trainer>>
    @Query("SELECT * FROM trainers WHERE id IN (:trainerIds)")
    fun getTrainersIn(trainerIds: List<String>): List<Trainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTrainers(trainers: List<Trainer>)

    @Query("SELECT * FROM product WHERE storeId = :storeId")
    fun getProducts(storeId: String): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertProducts(p: List<Product>)

    @Query("SELECT * FROM cartProduct")
    fun getCartProducts(): LiveData<List<CartProduct>>
    @Query("DELETE FROM cartProduct")
    fun cleanCartProduct()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCartProducts(p: List<CartProduct>)

    @Query("SELECT * FROM store")
    fun getStores(): LiveData<List<Store>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertStores(p: List<Store>)
}