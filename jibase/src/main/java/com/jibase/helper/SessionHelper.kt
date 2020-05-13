@file:Suppress("UNCHECKED_CAST")

package com.jibase.helper

object SessionHelper {
    private val listData = mutableMapOf<String, Any>()

    @JvmStatic
    fun put(key: String, data: Any) {
        listData[key] = data
    }

    @JvmStatic
    fun <T : Any> get(key: String): T? {
        return listData[key] as? T
    }

    @JvmStatic
    fun <T : Any> getNotNull(key: String, default: T): T {
        return listData.getOrPut(key, { default }) as T
    }

    /**
     *  get not null of data in session
     *  @param key
     *  @throw NullPointerException
     */
    @Throws(NullPointerException::class)
    @JvmStatic
    fun <T : Any> getNotNull(key: String): T {
        return get<T>(key) ?: throw NullPointerException("This data is null")
    }

    /**
     * Clear data of session
     * @param key: key of session, if key is empty then clear all session
     */

    @JvmStatic
    fun clear(key: String = "") {
        if (key.isNotEmpty()) {
            listData.remove(key)
        } else {
            listData.clear()
        }
    }

    /**
     * Check if key exists in session
     * @param key
     */
    @JvmStatic
    fun containKey(key: String): Boolean {
        return listData.containsKey(key)
    }
}