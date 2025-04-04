package org.example.project.core.domain

import org.example.project.core.domain.Error

sealed interface Result<out T , out E> {
    data class Success<out T>(val data: T) : Result<T , Nothing>
    data object Loading : Result<Nothing , Nothing>
    data object Idle : Result<Nothing , Nothing>
    data class Error<out E>(val error: E? = null) : Result<Nothing,E>
}

data class Response<out T>(
    val data: T? = null,
    val status: Status? = Status()
) {
    data class Status(
        val code: Int? = null,
        val error: String? = null
    )
}

fun <T,E,R> Result<T,E>.map(
    map: (T) -> R
) : Result<R,E>{
    return when(this){
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
        is Result.Loading -> Result.Loading
        is Result.Idle -> Result.Idle
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}


typealias EmptyResult<E> = Result<Unit, E>