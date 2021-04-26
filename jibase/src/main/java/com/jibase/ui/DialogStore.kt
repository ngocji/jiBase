package com.jibase.ui

class DialogStore : BaseViewModel() {
    private val properties = hashMapOf<String, Any>()

    fun addAll(map: Map<String, Any>) {
        if (properties.isEmpty()) {
            properties.putAll(map)
        }
    }

    fun <T : Any> get(key: String): T? {
        return properties[key] as? T
    }

    fun <T : Any> add(key: String, data: T?) {
        if (data != null) {
            properties[key] = data
        } else {
            properties.remove(key)
        }
    }
}