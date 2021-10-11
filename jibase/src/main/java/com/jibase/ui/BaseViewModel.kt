package com.jibase.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    open val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun Disposable.putToComposite() {
        compositeDisposable.add(this)
    }
}