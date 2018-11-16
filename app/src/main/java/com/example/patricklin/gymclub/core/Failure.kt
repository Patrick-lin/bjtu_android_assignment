package com.example.patricklin.gymclub.core

import android.util.Log
import java.lang.Exception

sealed class Failure {
    class Unknown(val error: Exception) : Failure()
    class UnknownApi(val error: String?): Failure()

    class NetworkConnection: Failure()

    abstract class FeatureFailure: Failure()

    class WrongCredentials : FeatureFailure()
    class UsernameAlreadyUsed : FeatureFailure()


    companion object {
        fun detect(err: Exception): Failure = when (err) {
            is java.net.ConnectException -> NetworkConnection()
            else -> { Log.i("failure-detect", "$err"); Unknown(err) }
        }

        fun detect(err: String?): Failure = when (err) {
            "username-already-used" -> UsernameAlreadyUsed()
            else -> { Log.i("failure-detect", "$err"); UnknownApi(err) }
        }
    }
}