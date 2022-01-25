package com.jibase.api

abstract class OnResultWrapperListener<T> {
    abstract fun onSuccess(data: T)
    open fun onLoading() {}
    open fun onError(throwable: Throwable?) {}
    open fun onEmptyPageData() {}
}