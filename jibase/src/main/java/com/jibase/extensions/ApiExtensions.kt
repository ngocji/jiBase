@file:JvmName("ApiExtensions")

package com.jibase.extensions

import com.jibase.api.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            ResultWrapper.Error(throwable)
        }
    }
}

suspend fun <T1, T2, T> safeApiCall2(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCallFirst: suspend () -> T1,
    apiCallSecond: suspend () -> T2,
    combination: (T1, T2) -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            val t1 = async { apiCallFirst() }
            val t2 = async { apiCallSecond() }
            val t = combination(t1.await(), t2.await())
            ResultWrapper.Success(t)
        } catch (throwable: Throwable) {
            ResultWrapper.Error(throwable)
        }
    }
}

suspend fun <T1, T2, T3, T> safeApiCall3(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCallFirst: suspend () -> T1,
    apiCallSecond: suspend () -> T2,
    apiCallThird: suspend () -> T3,
    combination: (T1, T2, T3) -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            val t1 = async { apiCallFirst() }
            val t2 = async { apiCallSecond() }
            val t3 = async { apiCallThird() }
            val t = combination(t1.await(), t2.await(), t3.await())
            ResultWrapper.Success(t)
        } catch (throwable: Throwable) {
            ResultWrapper.Error(throwable)
        }
    }
}