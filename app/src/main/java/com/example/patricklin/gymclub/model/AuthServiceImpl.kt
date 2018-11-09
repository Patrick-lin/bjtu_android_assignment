package com.example.patricklin.gymclub.model

import com.example.patricklin.gymclub.core.Either
import com.example.patricklin.gymclub.core.Failure
import com.example.patricklin.gymclub.core.UseCase
import kotlinx.coroutines.delay

import com.example.patricklin.gymclub.model.AuthService.*

class AuthServiceImpl : AuthService {
    private var token: String? = null

    override fun isLogged() = token != null
    override fun getToken(): String? = token

    override fun isValidUsername(username: String) = username.count() > 0
    override fun isValidPassword(password: String) = password.count() > 0

    override val logIn = object : UseCase<LogInResult, LogInInput>() {
        override suspend fun run(input: LogInInput): Either<Failure, LogInResult> {
            delay(1500)

            if (input.username == "admin" && input.password == "admin") {
                token = "admin_token"
                return Either.Right(LogInResult(success = true))
            }
            return Either.Left(Failure.WrongCredentials())
        }
    }

    override val register = object : UseCase<RegisterResult, RegisterInput>() {
        override suspend fun run(input: RegisterInput): Either<Failure, RegisterResult> {
            delay(1500)
            token = "user_token"
            return Either.Right(RegisterResult(success = true))
        }
    }

    override fun logOut() {
        token = null
    }
}
