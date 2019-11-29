package com.jibase.entities.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class ISingleLiveData<T> : LiveData<T>() {
    private val pending = AtomicBoolean(false)

    /**
     * NotifyDataChange value
     */
    fun notifyDataChanged() {
        value?.also {
            post(it)
        }
    }

    fun post(value: T) {
        pending.set(true)
        postValue(value)
    }

    /**
     *  check value in ilivedata
     *  @return true / false if data exists
     */
    fun hasData(): Boolean {
        return value != null
    }

    /**
     * @param default: T default
     * @return value or default
     */
    fun getNoNull(default: T): T {
        val data = value
        return if (data != null) {
            data
        } else {
            post(default)
            default
        }
    }

    /**
     * throw Exception
     */
    fun getNoNull(): T {
        return if (hasData()) value!! else throw Exception("The data is null")
    }


    /**
     *  get value data
     *  @return T or null
     */
    fun get(): T? {
        return value
    }

    /**
     * Change data in live data
     * @param action: task change data, return new value
     */
    fun change(action: (data: T?) -> T?) {
        postValue(action(value))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { onNext ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(onNext)
            }
        })
    }

    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }
}