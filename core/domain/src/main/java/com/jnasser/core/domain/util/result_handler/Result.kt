package com.jnasser.core.domain.util.result_handler

import com.jnasser.core.domain.util.error_handler.Error

/**
 * A sealed interface representing the result of an operation that can succeed or fail.
 *
 * @param D The type of data on success.
 * @param E The type of error on failure (must extend Error).
 */
sealed interface Result<out D, out E : Error> {

    /**
     * Represents a successful result holding the returned data.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed result holding an error object.
     */
    data class Error<out E : com.jnasser.core.domain.util.error_handler.Error>(
        val error: E
    ) : Result<Nothing, E>
}

/**
 * Maps the success value of the Result to a new type, preserving any error.
 *
 * @param map Lambda that transforms the success value from type T to type R.
 * @return A new Result with transformed data or the same error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Transforms the Result into a Result<Unit, E> by discarding the success value.
 *
 * @return An EmptyResult which wraps Unit on success or the same error on failure.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

/**
 * Type alias for a Result that contains no data on success.
 */
typealias EmptyResult<E> = Result<Unit, E>
