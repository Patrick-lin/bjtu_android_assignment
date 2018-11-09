package com.example.patricklin.gymclub.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> {
    abstract suspend fun run(input: Params): Either<Failure, Type>
    operator fun invoke(scope: CoroutineScope, input: Params, onResult: (Either<Failure, Type>) -> Unit) {
        val job = scope.async { run(input) }
        scope.launch { onResult(job.await()) }
    }
}

