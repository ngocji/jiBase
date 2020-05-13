package com.jibase.helper

import android.content.Context
import com.jibase.utils.Log
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

// region inline helper
inline fun <reified T> Context.registerEvent(noinline action: (data: T) -> Unit) {
    val id = this.hashCode()
    val eventClass = T::class.java
    RxBusHelper.register(id, eventClass, action)
}

fun Context.unregisterEvent() {
    RxBusHelper.unregister(this.hashCode())
}

fun <T : Any> sendEvent(data: T) {
    RxBusHelper.send(data)
}
// end region

object RxBusHelper {
    private val compositeDisposable: HashMap<Int, Disposable> by lazy { hashMapOf<Int, Disposable>() }
    private val busSubject: Subject<Any> by lazy { PublishSubject.create<Any>().toSerialized() }

    @Suppress("UNCHECKED_CAST")
    fun <T> register(id: Int, eventType: Class<T>, action: (data: T) -> Unit) {
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

    fun unregister(id: Int) {
        compositeDisposable[id]?.dispose()
    }

    @JvmStatic
    fun <T> register(context: Context, eventType: Class<T>, action: (data: T) -> Unit) {
        register(context.hashCode(), eventType, action)
    }

    @JvmStatic
    fun unregister(context: Context) {
        unregister(context.hashCode())
    }

    @JvmStatic
    fun <T : Any> send(data: T) {
        Log.d("RxEvent: \nsend event: $data")
        busSubject.onNext(data)
    }
}