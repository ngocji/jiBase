package com.jibase.helper

import com.jibase.utils.Log
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

// region inline helper
inline fun <reified T> Any.registerEvent(noinline action: (data: T) -> Unit) {
    val id = this.hashCode()
    val eventClass = T::class.java
    RxBusHelper.registerInternal(id, eventClass, action)
}

fun Any.unregisterEvent() {
    RxBusHelper.unregisterInternal(this.hashCode())
}

fun <T : Any> sendEvent(data: T) {
    RxBusHelper.send(data)
}
// end region

object RxBusHelper {
    private val compositeDisposable: HashMap<Int, Disposable> by lazy { hashMapOf<Int, Disposable>() }
    private val busSubject: Subject<Any> by lazy { PublishSubject.create<Any>().toSerialized() }

    @Suppress("UNCHECKED_CAST")
    fun <T> registerInternal(id: Int, eventType: Class<T>, action: (data: T) -> Unit) {
        Log.d("RxEvent: Register\nid: $id\neventClass: $eventType")
        busSubject
            .filter { event -> event.javaClass == eventType }
            .subscribe {
                val data = it.let {
                    it as? T ?: return@subscribe
                }
                action(data)
            }
            .also {
                compositeDisposable[id] = it
            }
    }

    fun unregisterInternal(id: Int) {
        compositeDisposable[id]?.dispose()
    }

    @JvmStatic
    fun <T> registerEvent(target: Any, eventType: Class<T>, action: (data: T) -> Unit) {
        registerInternal(target.hashCode(), eventType, action)
    }

    @JvmStatic
    fun unregisterEvent(target: Any) {
        unregisterInternal(target.hashCode())
    }

    @JvmStatic
    fun <T : Any> send(data: T) {
        Log.d("RxEvent: \nsend event: $data")
        busSubject.onNext(data)
    }
}