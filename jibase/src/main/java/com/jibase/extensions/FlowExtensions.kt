@file:JvmName("FlowExtensions")

package com.jibase.extensions

import androidx.lifecycle.*
import com.jibase.api.OnResultWrapperListener
import com.jibase.api.ResultWrapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

typealias SuccessHandler<T> = (value: T) -> Unit
typealias ErrorHandler = (error: Throwable?) -> Unit
typealias LoadingHandler = () -> Unit
typealias EmptyPageHandler = () -> Unit


fun <T> LifecycleCoroutineScope.collect(
    flow: StateFlow<ResultWrapper<T>>,
    successHandler: SuccessHandler<T>? = null,
    errorHandler: ErrorHandler? = null,
    loadingHandler: LoadingHandler? = null
) {
    launch {
        flow.collect {
            when (it) {
                is ResultWrapper.Success<*> -> {
                    successHandler?.invoke(it.takeOrThrow())
                }

                is ResultWrapper.Error -> {
                    errorHandler?.invoke(it.throwable)
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
    successHandler: SuccessHandler<T>? = null,
    errorHandler: ErrorHandler? = null,
    loadingHandler: LoadingHandler? = null,
    emptyHandler: EmptyPageHandler? = null
) {
    lifecycleScope.launchWhenStarted {
        flow.collect {
            when (it) {
                is ResultWrapper.Success<*> -> {
                    successHandler?.invoke(it.takeOrThrow())
                }

                is ResultWrapper.Error -> {
                    errorHandler?.invoke(it.throwable)
                }
                is ResultWrapper.Loading -> {
                    loadingHandler?.invoke()
                }
                is ResultWrapper.EmptyPage -> {
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
    nextPage: () -> Unit,
    callback: suspend () -> ResultWrapper<List<T>>
): MutableStateFlow<ResultWrapper<List<T>>> = MutableStateFlow(firstValue).apply {
    viewModelScope.launch {
        with(this@pagingFlow) {
            tryEmit(ResultWrapper.Loading)

            when (val result = callback.invoke()) {
                is ResultWrapper.Success -> {
                    if (result.takeOrThrow().isEmpty()) {
                        tryEmit(ResultWrapper.EmptyPage)
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


// support java

fun <T> LifecycleCoroutineScope.collect(
    flow: StateFlow<ResultWrapper<T>>,
    listener: OnResultWrapperListener<T>? = null
) {
    this.collect(flow,
        successHandler = {
            listener?.onSuccess(it)
        },
        errorHandler = { listener?.onError(it) },
        loadingHandler = {
            listener?.onLoading()
        })
}


fun <T> LifecycleOwner.pagingCollect(
    flow: StateFlow<ResultWrapper<T>>,
    listener: OnResultWrapperListener<T>? = null
) {
    this.pagingCollect(flow,
        successHandler = {
            listener?.onSuccess(it)
        },
        errorHandler = { listener?.onError(it) },
        loadingHandler = {
            listener?.onLoading()
        },
        emptyHandler = {
            listener?.onEmptyPageData()
        })
}


fun <T> ViewModel.resultFlow(
    callback: suspend () -> ResultWrapper<T>
) = resultFlow(ResultWrapper.None, callback)

fun <T> ViewModel.pagingFlow(
    nextPage: Runnable,
    callback: suspend () -> ResultWrapper<List<T>>
) = pagingFlow(
    ResultWrapper.None,
    {
        nextPage.run()
    }, callback
)