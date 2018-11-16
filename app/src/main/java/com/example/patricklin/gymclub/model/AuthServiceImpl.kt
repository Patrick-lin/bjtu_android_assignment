package com.example.patricklin.gymclub.model

import com.example.patricklin.gymclub.core.Either
import com.example.patricklin.gymclub.core.Failure
import com.example.patricklin.gymclub.core.UseCase

import com.example.patricklin.gymclub.model.UserApi.*
import java.lang.Exception

class AuthServiceImpl(val userApi: UserApi) : AuthService {

    private var token: String? = null

    override fun isLogged() = token != null
    override fun getToken(): String? = token
    override fun getAuthHeader(): String = "TOKEN $token"

    override fun isValidUsername(username: String) = username.count() > 0
    override fun isValidPassword(password: String) = password.count() > 0

    override val logIn = object : UseCase<AuthResult, LogInInput>() {
        override suspend fun run(input: LogInInput): Either<Failure, AuthResult> {
            try {
                val res = userApi.logIn(input).await()
                if (res.success && res.token != null) {
                    token = res.token
                    return Either.Right(res)
                }

                return Either.Left(Failure.WrongCredentials())
            } catch (err: Exception) {
                return Either.Left(Failure.detect(err))
            }
        }
    }

    override val register = object : UseCase<AuthResult, UserApi.RegisterInput>() {
        override suspend fun run(input: UserApi.RegisterInput): Either<Failure, AuthResult> {
            try {
                val res = userApi.register(input).await()
                if (res.success && res.token != null) {
                    token = res.token
                    return Either.Right(res)
                }
                return Either.Left(Failure.detect(res.message))
            } catch (err: Exception) {
                return Either.Left(Failure.detect(err))
            }
        }
    }

    override fun logOut() {
        token = null
    }
}
