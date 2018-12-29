package com.example.patricklin.gymclub.model.store

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StoreApi {
    data class GetSessionsResult(
            val list: List<Store>,
            val error: String?
    )
    @GET("/stores")
    fun getStores(@Header("authorization") token: String): Deferred<GetSessionsResult>


    data class GetProductsResult(
            val list: List<Product>
    )
    @GET("/products/{storeId}/list")
    fun getProducts(@Path("storeId") storeId: String): Deferred<GetProductsResult>

    @GET("/cart")
    fun getCart(@Header("authorization") token: String?): Deferred<Cart>

    @POST("/product/{productId}/add")
    fun addProduct(@Header("authorization") token: String?, @Path("productId") productId: String): Deferred<Cart>

    @POST("/buy")
    fun buy(@Header("authorization") token: String?): Deferred<Cart>
}