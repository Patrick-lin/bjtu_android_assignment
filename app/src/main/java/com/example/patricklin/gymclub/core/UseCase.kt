package com.example.patricklin.gymclub.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> {
    open suspend fun run(input: Params): Either<Failure, Type> {
        TODO("implement run")
    }

    open suspend fun run(scope: CoroutineScope, input: Params) = run(input)

    operator fun invoke(scope: CoroutineScope, input: Params, onResult: (Either<Failure, Type>) -> Unit) {
        val job = scope.async { run(scope, input) }
        scope.launch { onResult(job.await()) }
    }
}

