package com.jibase.api


sealed class ResultWrapper<out T> {
    object None : ResultWrapper<Nothing>()

    object Loading : ResultWrapper<Nothing>()

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class Error(val throwable: Throwable? = null) : ResultWrapper<Nothing>()


    object EmptyPage : ResultWrapper<Nothing>()


    @Throws(Exception::class)
    fun takeOrThrow(): T {
        return when (this) {
            is Success -> value
            is Error -> throw throwable ?: Throwable()
            else -> throw Throwable()
        }
    }
}