package com.jibase.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class ILiveData<T>(data: T? = null, isNotifyDataWhenInitialize: Boolean = false) : LiveData<T>(data) {
    private val pendingFirstInitialize = AtomicBoolean(isNotifyDataWhenInitialize)

    init {
        if (data != null && isNotifyDataWhenInitialize) {
            notifyDataChanged()
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { onNext ->
            if (pendingFirstInitialize.get()) {
                observer.onChanged(onNext)
            }

            // reset
            pendingFirstInitialize.set(true)
        })
    }

    fun post(data: T?) {
        postValue(data)
    }

    fun change(action: (data: T?) -> T?) {
        post(action.invoke(value))
    }

    fun notifyDataChanged() {
        post(value)
    }

    fun hasData(): Boolean {
        return value != null
    }

    fun getNotNull(default: T): T {
        val data = value
        return if (data != null) {
            data
        } else {
            value = default
            default
        }
    }

    fun get(): T? {
        return value
    }
}