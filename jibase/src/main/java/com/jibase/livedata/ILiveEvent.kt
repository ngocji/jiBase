package com.jibase.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("unused")
class ILiveEvent<T> private constructor() : LiveData<T>() {
    companion object {
        fun <T> newInstance(): ILiveEvent<T> {
            return ILiveEvent()
        }
    }

    private val pending = hashMapOf<Int, AtomicBoolean>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, object : Observer<T> {
            init {
                pending[this.hashCode()] = AtomicBoolean(false)
            }

            override fun onChanged(t: T) {
                val shouldNotify = pending.getOrPut(this.hashCode()) { AtomicBoolean(false) }
                if (shouldNotify.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }
        })
    }

    override fun setValue(value: T) {
        pending.values.forEach { it.set(true) }
        super.setValue(value)
    }

    override fun removeObserver(observer: Observer<in T>) {
        super.removeObserver(observer)
        pending.remove(observer.hashCode())
    }

    @MainThread
    fun invalidateValue(value: T) {
        setValue(value)
    }

    fun post(value: T) {
        pending.values.forEach { it.set(true) }
        postValue(value)
    }

    fun post() {
        pending.values.forEach { it.set(true) }
        postValue(null)
    }
}