package com.jibase.helper.eventbus

import org.greenrobot.eventbus.EventBus

object EventHelper {
    fun register(target: Any) {
        if (!EventBus.getDefault().isRegistered(target)) {
            EventBus.getDefault().register(target)
        }
    }

    fun unregister(target: Any) {
        EventBus.getDefault().unregister(target)
    }

    fun post(event: Any, isSticky: Boolean = false) {
        when (isSticky) {
            true -> EventBus.getDefault().post(event)
            else -> EventBus.getDefault().postSticky(event)
        }
    }
}