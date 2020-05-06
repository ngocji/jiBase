package com.jibase.helper.pref

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject

object SharePreferencesHelper : KoinComponent {
    private val context: Context by inject()
    val pref: SharedPreferences by lazy { context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE) }

    inline fun <reified T> getPref(key: String, defaultValue: T): T {
        val value = pref.getString(key, defaultValue.toString()) ?: return defaultValue
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

    inline fun <reified T> putPref(key: String, value: T) {
        with(pref.edit()) {
            putString(key, value.toString())
            apply()
        }
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }
}