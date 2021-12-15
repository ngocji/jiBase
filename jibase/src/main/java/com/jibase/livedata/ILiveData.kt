package com.jibase.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class ILiveData<T> constructor(data: T? = null, isNotifyDataWhenInitialize: Boolean = true) :
    LiveData<T>() {
    companion object {
        @JvmStatic
        fun <T> newInstance(
            data: T? = null,
            isNotifyDataWhenInitialize: Boolean = true
        ): ILiveData<T> {
            return ILiveData(data = data, isNotifyDataWhenInitialize = isNotifyDataWhenInitialize)
        }

        @JvmStatic
        fun <T> newInstance(): ILiveData<T> {
            return ILiveData()
        }
    }

    private val pendingFirstInitialize = AtomicBoolean(isNotifyDataWhenInitialize)

    init {
        set(data)
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

    fun setIfNotNull(data: T?) {
        data?.also {
            value = it
        }
    }

    fun set(data: T?) {
        value = data
    }
}