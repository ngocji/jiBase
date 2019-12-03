package com.jibase.entities.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class ILiveData<T>(data: T? = null, isNotifyData: Boolean = false) : LiveData<T>(data) {
    private val pendingFirstInitialize = AtomicBoolean(isNotifyData)

    init {
        if (data != null && isNotifyData) {
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
            post(default)
            default
        }
    }

    fun get(): T? {
        return value
    }
}