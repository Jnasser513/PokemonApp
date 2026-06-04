package com.jnasser.core.domain.util.error_handler

sealed interface DataError : Error {
    data object Network : DataError
    data object Database : DataError
    data object Serialization : DataError
    data object Unknown : DataError
}
