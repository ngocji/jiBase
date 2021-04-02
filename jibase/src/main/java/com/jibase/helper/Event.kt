package com.jibase.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jibase.livedata.ILiveEvent

object Event {
    private val events by lazy { hashMapOf<Class<*>, ILiveEvent<Any>>() }

    @JvmStatic
    fun <T : Any> observe(cls: Class<T>, viewLifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        getEvent<T>(cls).observe(viewLifecycleOwner, Observer {
            observer.onChanged(it as T)
        })
    }

    @JvmStatic
    fun <T : Any> post(data: T) {
        getEvent<T>(data::class.java).post(data)
    }

    private fun <T : Any> getEvent(clazz: Class<*>): ILiveEvent<Any> {
        return events.getOrPut(clazz, { ILiveEvent.newInstance() })
    }
}