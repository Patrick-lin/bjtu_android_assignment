package com.example.patricklin.gymclub.model

import android.util.Log
import com.example.patricklin.gymclub.core.Either
import com.example.patricklin.gymclub.core.Failure
import com.example.patricklin.gymclub.core.UseCase
import com.example.patricklin.gymclub.model.user.User
import com.example.patricklin.gymclub.model.user.UserApi

import com.example.patricklin.gymclub.model.user.UserApi.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.lang.Exception

class AuthServiceImpl(val userApi: UserApi, val shopDao: ShopDao) : AuthService {

    private var token: String? = null

    override suspend fun syncDb() {
        val user = GlobalScope.async { shopDao.getUser() }.await()
        Log.i("test", "$user")
        token = user?.token
    }

    override fun isLogged() = getToken() != null
    override fun getToken(): String? = token
    override fun getAuthHeader(): String = "TOKEN $token"

    override fun isValidUsername(username: String) = username.count() > 0
    override fun isValidPassword(password: String) = password.count() > 0

    override val logIn = object : UseCase<AuthResult, LogInInput>() {
        override suspend fun run(input: LogInInput): Either<Failure, AuthResult> {
            try {
                val res = userApi.logIn(input).await()
                if (res.success && res.token != null) {
                    token = res.token;
                    GlobalScope.async { shopDao.createUser(User(token = res.token)) }.await()
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
                    token = res.token;
                    GlobalScope.async { shopDao.createUser(User(token = res.token)) }.await()
                    return Either.Right(res)
                }
                return Either.Left(Failure.detect(res.message))
            } catch (err: Exception) {
                return Either.Left(Failure.detect(err))
            }
        }
    }

    override suspend fun logOut() {
        GlobalScope.async { shopDao.deleteUser() }.await()
        token = null
    }
}
