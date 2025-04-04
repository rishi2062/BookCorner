package org.example.project.core.domain

interface Error {

}

sealed interface DataError : Error{
    enum class Remote : DataError{
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN_ERROR
    }
    enum class Local : DataError{
        DISK_FULL
    }
}