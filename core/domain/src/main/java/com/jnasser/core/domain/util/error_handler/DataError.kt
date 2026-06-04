package com.jnasser.core.domain.util.error_handler

/**
 * Represents different types of data-related errors.
 * Categorizes errors into network and local error types.
 */
sealed interface DataError: Error {

    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUEST,
        NOT_FOUND,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL
    }
}