package com.jnasser.core.domain.util.result_handler

import com.jnasser.core.domain.util.error_handler.Error

sealed interface Result<out T, out E : Error> {
    data class Success<T>(val value: T) : Result<T, Nothing>
    data class Failure<E : Error>(val error: E) : Result<Nothing, E>
}
