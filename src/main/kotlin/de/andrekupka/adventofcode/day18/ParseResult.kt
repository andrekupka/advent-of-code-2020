package de.andrekupka.adventofcode.day18

sealed class ParseResult<out T>

data class SuccessResult<out T>(
    val value: T,
    val nextPosition: Int
) : ParseResult<T>()

fun <T> T.asSuccess(nextPosition: Int) = SuccessResult(this, nextPosition)

data class ErrorResult(
    val message: String
) : ParseResult<Nothing>()

fun <T, R> ParseResult<T>.map(transform: (T) -> R) = when(this) {
    is SuccessResult -> SuccessResult(transform(value), nextPosition)
    is ErrorResult -> this
}

fun <T, R> ParseResult<T>.flatMap(transform: (T, Int) -> ParseResult<R>) = when(this) {
    is SuccessResult -> transform(value, nextPosition)
    is ErrorResult -> this
}