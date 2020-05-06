package com.jibase.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class ISingleLiveData<T>(data: T? = null, isNotifyData: Boolean = false) : ILiveData<T>(data, isNotifyData) {

    private val pending = AtomicBoolean(isNotifyData)


    override fun postValue(value: T) {
        pending.set(true)
        super.postValue(value)
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