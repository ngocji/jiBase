@file:JvmName("LiveDataExtensions")

package com.jibase.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

inline fun <reified L : LiveData<T>, T> Fragment.observe(
    livedata: LiveData<T>?, noinline block: (T) -> (Unit)
) {
    livedata?.observe(viewLifecycleOwner, Observer(block))
}

inline fun <reified L : LiveData<T>, T> Fragment.onceObserve(
    livedata: LiveData<T>?, noinline block: (T) -> (Unit)
) {
    livedata?.observe(viewLifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            livedata.removeObserver(this)
            block.invoke(t)
        }
    })
}

inline fun <reified L : LiveData<T>, T> AppCompatActivity.observe(
    livedata: LiveData<T>, noinline block: (T) -> (Unit)
) {
    livedata.observe(this, Observer(block))
}