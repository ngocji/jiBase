package com.jibase.helper.event

import android.content.Context
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

// inline helper
inline fun <reified T> Context.registerEvent(noinline action: (data: T) -> Unit) {
    val id = this.hashCode()
    val eventClass = T::class.javaClass
    RxBusHelper.register(id, eventClass, action)
}

fun Context.unregisterEvent() {
    RxBusHelper.unregister(this.hashCode())
}

object RxBusHelper {
    private val compositeDisposable: HashMap<Int, Disposable> by lazy { hashMapOf() }
    private val busSubject: Subject<Any> by lazy { PublishSubject.create<Any>().toSerialized() }

    @Suppress("UNCHECKED_CAST")
    fun <C, T> register(id: Int, eventClass: Class<C>, action: (data: T) -> Unit) {
        busSubject
            .filter { event -> event.javaClass.kotlin == eventClass }
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
}