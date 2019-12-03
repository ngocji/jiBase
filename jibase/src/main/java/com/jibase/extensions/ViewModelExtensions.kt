package com.jibase.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

inline fun <reified VM : ViewModel> getViewModelFromActivity(activity: FragmentActivity): VM {
    return ViewModelProvider(activity).get(VM::class.java)
}


fun <VM : ViewModel> ViewModelStoreOwner.getViewModel(clazz: Class<VM>): VM {
    return ViewModelProvider(this).get(clazz)
}

fun <VM : LiveData<T>, T> VM.observe(lifecycleOwner: LifecycleOwner, onChange: (data: T?) -> Unit) {
    this.observe(lifecycleOwner, Observer<T> {
        onChange.invoke(it)
    })
}