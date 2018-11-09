package com.example.patricklin.gymclub.model

import com.example.patricklin.gymclub.core.UseCase

interface AuthService {
    fun isLogged(): Boolean
    fun getToken(): String?


    fun isValidUsername(username: String): Boolean
    fun isValidPassword(password: String): Boolean

    data class LogInInput(val username: String, val password: String)
    data class LogInResult(val success: Boolean)
    val logIn: UseCase<LogInResult, LogInInput>

    data class RegisterInput(val username: String, val password: String)
    data class RegisterResult(val success: Boolean)
    val register: UseCase<RegisterResult, RegisterInput>


    fun logOut()
}