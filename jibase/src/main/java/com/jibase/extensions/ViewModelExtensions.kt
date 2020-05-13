@file:JvmName("ViewModelExtensions")

package com.jibase.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <VM : LiveData<T>, T> VM.observe(lifecycleOwner: LifecycleOwner, onChange: (data: T?) -> Unit) {
    this.observe(lifecycleOwner, Observer<T> {
        onChange.invoke(it)
    })
}