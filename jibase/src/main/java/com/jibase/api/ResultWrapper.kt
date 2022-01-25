package com.jibase.api

import java.io.IOException

sealed class ResultWrapper<out T> {

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class GenericError(val throwable: Throwable? = null) : ResultWrapper<Nothing>()

    data class HttpError<out T>(val errorBody: T? = null) : ResultWrapper<Nothing>()

    data class EmptyData(val throwable: Throwable?) : ResultWrapper<Nothing>()

    object None : ResultWrapper<Nothing>()

    object Loading : ResultWrapper<Nothing>()

    object Empty : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()

    @Throws(Exception::class)
    fun takeValueOrThrow(): T {
        return when (this) {
            is Success -> value
            is GenericError -> throw throwable ?: Throwable()
            is NetworkError -> throw IOException()
            else -> throw Throwable("Unknown the result type")
        }
    }
}