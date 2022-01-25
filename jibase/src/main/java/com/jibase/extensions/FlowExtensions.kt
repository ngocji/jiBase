package com.jibase.extensions

import androidx.lifecycle.*
import com.jibase.api.ResultWrapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

typealias ErrorHandler = (exception: Throwable?) -> Unit
typealias SuccessHandler<T> = (value: T) -> Unit
typealias LoadingHandler = () -> Unit
typealias HttpErrorHandler<T> = (error: T?) -> Unit
typealias EmptyHandler = () -> Unit

fun <T> LifecycleCoroutineScope.collect(
    flow: StateFlow<ResultWrapper<T>>,
    errorHandler: ErrorHandler? = null,
    httpErrorHandler: HttpErrorHandler<T>? = null,
    successHandler: SuccessHandler<T>? = null,
    loadingHandler: LoadingHandler? = null
) {
    launch {
        flow.collect {
            when (it) {
                is ResultWrapper.Success<*> -> {
                    successHandler?.invoke(it.takeValueOrThrow())
                }
                is ResultWrapper.GenericError -> {
                    errorHandler?.invoke(it.throwable)
                }
                is ResultWrapper.HttpError -> {
                    httpErrorHandler?.invoke(it.errorBody)
                }
                is ResultWrapper.Loading -> {
                    loadingHandler?.invoke()
                }
                else -> {
                }
            }
        }
    }
}

fun <T> LifecycleOwner.pagingCollect(
    flow: StateFlow<ResultWrapper<T>>,
    errorHandler: ErrorHandler?,
    successHandler: SuccessHandler<T>?,
    loadingHandler: LoadingHandler?,
    emptyHandler: EmptyHandler?
) {
    lifecycleScope.launchWhenStarted {
        flow.collect {
            when (it) {
                is ResultWrapper.Success<*> -> {
                    successHandler?.invoke(it.takeValueOrThrow())
                }
                is ResultWrapper.GenericError -> {
                    errorHandler?.invoke(it.throwable)
                }
                is ResultWrapper.Loading -> {
                    loadingHandler?.invoke()
                }
                is ResultWrapper.Empty -> {
                    emptyHandler?.invoke()
                }
                else -> {
                }
            }
        }
    }
}

fun <T> ViewModel.resultFlow(
    firstValue: ResultWrapper<T> = ResultWrapper.None,
    callback: suspend () -> ResultWrapper<T>
): MutableStateFlow<ResultWrapper<T>> = MutableStateFlow(firstValue).apply {
    viewModelScope.launch {
        with(this@resultFlow) {
            tryEmit(ResultWrapper.Loading)
            value = callback.invoke()
        }
    }
}

fun <T> ViewModel.pagingFlow(
    firstValue: ResultWrapper<List<T>> = ResultWrapper.None,
    isFistPage: () -> Boolean,
    nextPage: () -> Unit,
    callback: suspend () -> ResultWrapper<List<T>>
): MutableStateFlow<ResultWrapper<List<T>>> = MutableStateFlow(firstValue).apply {
    viewModelScope.launch {
        with(this@pagingFlow) {
            if (isFistPage.invoke()) {
                tryEmit(ResultWrapper.Loading)
            }
            when (val result = callback.invoke()) {
                is ResultWrapper.Success -> {
                    if (isFistPage.invoke() && result.takeValueOrThrow().isEmpty()) {
                        tryEmit(ResultWrapper.Empty)
                    } else {
                        nextPage.invoke()
                        tryEmit(result)
                    }
                }
                else -> {
                    tryEmit(result)
                }
            }
        }
    }
}