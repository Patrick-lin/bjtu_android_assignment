package com.example.patricklin.gymclub.model.store

import android.arch.lifecycle.LiveData

interface StoreService {
    fun getSessions(): LiveData<List<Store>>

    fun getLiveSession(id: String): LiveData<Store>
    fun getSession(id: String): Store?

    fun getProducts(storeId: String): LiveData<List<Product>>

    fun getCartProducts(): LiveData<List<CartProduct>>

    suspend fun addProduct(productId: String)

    fun buy()
}