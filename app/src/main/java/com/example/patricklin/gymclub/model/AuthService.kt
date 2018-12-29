package com.example.patricklin.gymclub.model

import com.example.patricklin.gymclub.core.UseCase
import com.example.patricklin.gymclub.model.user.UserApi

interface AuthService {
    suspend fun syncDb()
    fun isLogged(): Boolean
    fun getToken(): String?
    fun getAuthHeader(): String

    fun isValidUsername(username: String): Boolean
    fun isValidPassword(password: String): Boolean

    val logIn: UseCase<UserApi.AuthResult, UserApi.LogInInput>
    val register: UseCase<UserApi.AuthResult, UserApi.RegisterInput>

    suspend fun logOut()
}