package com.example.patricklin.gymclub.model

import com.squareup.moshi.Json
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface UserApi {
    data class AuthResult(val success: Boolean, val token: String?, val message: String?)

    data class LogInInput(
            @Json(name = "username") val username: String,
            @Json(name = "password") val password: String
    )

    @Headers("Content-Type: application/json")
    @POST("/login")
    fun logIn(@Body input: LogInInput): Deferred<AuthResult>


    data class RegisterInput(val username: String, val password: String)
    @POST("register")
    fun register(@Body input: RegisterInput): Deferred<AuthResult>

    @GET("user/me")
    fun me(@Header("authorization") token: String): Deferred<List<User>>
}