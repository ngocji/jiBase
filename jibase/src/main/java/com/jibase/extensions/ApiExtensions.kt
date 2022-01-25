package com.jibase.extensions

import com.jibase.api.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

import com.google.gson.Gson
import kotlinx.coroutines.async
import java.lang.Exception


suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                ResultWrapper.HttpError(parseError(throwable))
            } else {
                ResultWrapper.GenericError(throwable)
            }
        }
    }
}

suspend fun <T1, T2, T> safeApiCall2(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCallFirst: suspend () -> T1,
    apiCallSecond: suspend () -> T2,
    combination: (T1, T2) -> T
) : ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            val t1 = async { apiCallFirst() }
            val t2 = async { apiCallSecond() }
            val t = combination(t1.await(), t2.await())
            ResultWrapper.Success(t)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                ResultWrapper.HttpError(parseError(throwable))
            } else {
                ResultWrapper.GenericError(throwable)
            }
        }
    }
}

suspend fun <T1, T2, T3, T> safeApiCall3(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCallFirst: suspend () -> T1,
    apiCallSecond: suspend () -> T2,
    apiCallThird: suspend () -> T3,
    combination: (T1, T2, T3) -> T
) : ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            val t1 = async { apiCallFirst() }
            val t2 = async { apiCallSecond() }
            val t3 = async { apiCallThird() }
            val t = combination(t1.await(), t2.await(), t3.await())
            ResultWrapper.Success(t)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                ResultWrapper.HttpError(parseError(throwable))
            } else {
                ResultWrapper.GenericError(throwable)
            }
        }
    }
}

fun <T> parseError(httpException: HttpException) : T? {
    return try {
        Gson().fromJson(
            httpException.response()?.errorBody()?.string(), ErrorBody::class.java
        )
    } catch (exc: Exception) {
        null
    }
}