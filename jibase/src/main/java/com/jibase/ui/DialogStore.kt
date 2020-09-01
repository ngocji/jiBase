package com.jibase.ui

import androidx.lifecycle.ViewModel

class DialogStore : ViewModel() {
    private val properties = hashMapOf<String, Any>()

    fun add(map: Map<String, Any>) {
        if (properties.isEmpty()) {
            properties.putAll(map)
        }
    }

    fun <T : Any> get(key: String): T? {
        return properties[key] as? T
    }
}