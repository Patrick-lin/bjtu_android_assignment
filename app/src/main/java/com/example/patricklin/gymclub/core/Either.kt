package com.example.patricklin.gymclub.core

sealed class Either<out LeftValue, out RightValue> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out LeftValue>(val a: LeftValue) : Either<LeftValue, Nothing>()
    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out RightValue>(val b: RightValue) : Either<Nothing, RightValue>()

    val isRight get() = this is Right<RightValue>
    val isLeft get() = this is Left<LeftValue>

    /** * */
    fun <LeftValue> left(leftValue: LeftValue) = Either.Left(leftValue)
    fun <RightValue> right(rightValue: RightValue) = Either.Right(rightValue)

    fun either(fnLeft: (LeftValue) -> Any, fnRight: (RightValue) -> Any): Any = when (this) {
        is Left -> fnLeft(a)
        is Right -> fnRight(b)
    }
}
