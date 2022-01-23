package com.jibase.pref

import android.content.Context
import android.content.SharedPreferences
import com.jibase.helper.GsonManager
import com.jibase.utils.Log

class SharePref(context: Context, prefName: String) {
    val pref: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    inline fun <reified T> get(key: String, defaultValue: T? = null): T? =
        get(key, defaultValue, T::class.java)

    fun <T> get(key: String, clazz: Class<T>): T? = get(key, null, clazz)

    fun <T> get(key: String, defaultValue: T?, clazz: Class<out T>): T? {
        val value = pref.getString(key, defaultValue.toString()) ?: return defaultValue
        return when (clazz) {
            Boolean::class -> value.toBoolean() as T
            Float::class -> value.toFloat() as T
            Int::class -> value.toInt() as T
            Long::class -> value.toLong() as T
            String::class -> value as T
            Double::class -> value.toDouble() as T
            else -> {
                try {
                    GsonManager.fromJson(value)
                } catch (e: Exception) {
                    defaultValue
                }
            }
        }
    }

    // region get for java

    fun getBoolean(key: String, defaultValue: Boolean) = get(key, defaultValue)
    fun getLong(key: String, defaultValue: Long) = get(key, defaultValue)
    fun getFloat(key: String, defaultValue: Float) = get(key, defaultValue)
    fun getDouble(key: String, defaultValue: Double) = get(key, defaultValue)
    fun getString(key: String, defaultValue: String) = get(key, defaultValue)
    fun getInt(key: String, defaultValue: Int) = get(key, defaultValue)
    fun <T> getObject(key: String, defaultValue: T?, clazz: Class<T>) =
        get(key, defaultValue, clazz)

    // end region


    fun <T> put(key: String, value: T?) {
        with(pref.edit()) {
            when (value) {
                is Boolean, is Float, is Int, is Long, is String, is Double -> {
                    Log.d("share: push normal data")
                    putString(key, value.toString())
                }
                else -> {
                    Log.d("share: push object data")
                    if (value == null) {
                        remove(key)
                    } else {
                        putString(key, GsonManager.toJson(value))
                    }
                }
            }

            apply()
        }
    }
}