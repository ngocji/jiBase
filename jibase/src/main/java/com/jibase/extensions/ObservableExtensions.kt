@file:JvmName("ObservableExtensions")

package com.jibase.extensions

import com.jibase.ui.BindViewModel
import io.reactivex.disposables.Disposable

fun <V : BindViewModel> Disposable.attachToComposite(viewModel: V) {
    viewModel.compositeDisposable.add(this)
}

fun <V : BindViewModel> attachToComposite(viewModel: V, disposable: Disposable) {
    disposable.attachToComposite(viewModel)
}
