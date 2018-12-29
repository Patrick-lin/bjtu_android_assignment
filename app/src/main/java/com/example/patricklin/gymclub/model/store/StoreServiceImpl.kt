package com.example.patricklin.gymclub.model.store

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.ShopDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoreServiceImpl(
        private val shopDao: ShopDao,
        private val storeApi: StoreApi,
        private val authService: AuthService) : StoreService {
    private val classes = MutableLiveData<List<Store>>()

    override fun getSessions(): LiveData<List<Store>> {
        GlobalScope.launch {
            try {
                val res = storeApi.getStores(authService.getAuthHeader()).await()
                classes.postValue(res.list)
                shopDao.upsertStores(res.list)
            } catch (err: Throwable) {
                Log.d("storeApi", "$err")
            }
        }
        return shopDao.getStores()
    }

    override fun getLiveSession(id: String): LiveData<Store> = Transformations.map(shopDao.getStores()) {
        it.find { c -> c.id == id }
    }

    override fun getCartProducts(): LiveData<List<CartProduct>> {
        GlobalScope.launch {
            try {
                val res = storeApi.getCart(authService.getAuthHeader()).await()
                if (res != null) {
                    shopDao.cleanCartProduct()
                    shopDao.upsertCartProducts(res.products)
                }
            } catch (err: Throwable) {
                Log.e("test", "$err")
            }
        }

        return shopDao.getCartProducts()
    }

    override fun getSession(id: String): Store? = classes.value.orEmpty().find { c -> c.id == id }

    override fun getProducts(storeId: String): LiveData<List<Product>> {
        GlobalScope.launch {
            try {
                val res = storeApi.getProducts(storeId).await()
                if (res != null && res.list != null) {
                    shopDao.upsertProducts(res.list)
                }
            } catch (err: Throwable) {
                Log.e("test", "$err")
            }
        }
        return shopDao.getProducts(storeId)
    }

    override suspend fun addProduct(productId: String) {
        try {
            val res = storeApi.addProduct(authService.getAuthHeader(), productId).await()
            GlobalScope.launch {
                shopDao.cleanCartProduct()
                shopDao.upsertCartProducts(res.products)
            }
        } catch (err: Throwable) {
            Log.e("test", "$err")
        }
    }

    override fun buy() {
        try {
            GlobalScope.launch {
                try {
                    val res = storeApi.buy(authService.getAuthHeader()).await()
                    shopDao.cleanCartProduct()
                    shopDao.upsertCartProducts(res.products)
                } catch (err: Throwable) {
                    Log.e("test", "$err")
                }
            }
        } catch (err: Throwable) {
            Log.e("test", "$err")
        }
    }

}