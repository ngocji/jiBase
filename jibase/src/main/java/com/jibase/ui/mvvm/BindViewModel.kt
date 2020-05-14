package com.jibase.ui.mvvm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.Koin
import org.koin.core.KoinComponent

open class BindViewModel : ViewModel(), KoinComponent {
    open val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun getKoin(): Koin {
        return super.getKoin()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}