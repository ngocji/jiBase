@file:JvmName("ObservableExtensions")

package com.jibase.extensions

import com.jibase.ui.BaseViewModel
import io.reactivex.disposables.Disposable

fun <V : BaseViewModel> Disposable.attachToComposite(viewModel: V) {
    viewModel.compositeDisposable.add(this)
}

fun <V : BaseViewModel> attachToComposite(viewModel: V, disposable: Disposable) {
    disposable.attachToComposite(viewModel)
}
