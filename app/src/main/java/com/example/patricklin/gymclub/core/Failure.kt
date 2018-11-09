package com.example.patricklin.gymclub.core

sealed class Failure {
    class NetworkConnection: Failure()

    abstract class FeatureFailure: Failure()

    class WrongCredentials : FeatureFailure()
}