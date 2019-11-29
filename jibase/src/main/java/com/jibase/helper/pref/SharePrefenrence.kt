package com.jibase.helper.pref

import android.content.Context
import android.content.SharedPreferences
import com.jibase.BaseApp

object SharePreferencesHelper {
    val pref: SharedPreferences = BaseApp.instance.getSharedPreferences(BaseApp.instance.packageName, Context.MODE_PRIVATE)

    inline fun <reified T> get(key: String, defaultValue: T): T {
        return pref.get(key, defaultValue)
    }

    inline fun <reified T> put(key: String, value: T) {
        pref.put(key, value)
    }

    /**
     * Register listener sharePreferences
     * @param listener
     */
    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Un Register listener sharePreferences
     * @param listener
     */
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }
}

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    val value = getString(key, defaultValue.toString()) ?: ""
    return when (T::class) {
        Boolean::class -> value.toBoolean() as T
        Float::class -> value.toFloat() as T
        Int::class -> value.toInt() as T
        Long::class -> value.toLong() as T
        String::class -> value as T
        Double::class -> value.toDouble() as T
        else -> defaultValue
    }
}

inline fun <reified T> SharedPreferences.put(key: String, value: T) {
    val editor = this.edit()
    editor.putString(key, value.toString())
    editor.apply()
}