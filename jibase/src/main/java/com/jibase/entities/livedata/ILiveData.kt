package com.jibase.entities.livedata

import android.os.Looper
import androidx.lifecycle.MutableLiveData

class ILiveData<T>(default: T? = null, isNotifyData: Boolean = false) :
    MutableLiveData<T>(default) {
    init {
        if (default != null && isNotifyData) {
            notifyDataChanged()
        }
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
            value = default
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
        if (isRunningMainThread()) {
            value = action(value)
        } else {
            postValue(action(value))
        }
    }

    /**
     * NotifyDataChange value
     */
    fun notifyDataChanged() {
        if (isRunningMainThread()) {
            value = value
        } else {
            postValue(value)
        }
    }

    /**
     * set value ILiveData
     * @param data: T
     */
    fun post(data: T?) {
        if (isRunningMainThread()) {
            value = data
        } else {
            postValue(data)
        }
    }


    private fun isRunningMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}