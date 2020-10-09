package com.jibase.utils

import android.content.Context
import android.content.SharedPreferences
import com.jibase.BaseApp
import com.jibase.helper.GsonHelper

object SharePreferencesUtils {
    val pref: SharedPreferences by lazy {
        BaseApp.applicationContext.getSharedPreferences(
            BaseApp.applicationContext.packageName,
            Context.MODE_PRIVATE
        )
    }

    inline fun <reified T> getPref(key: String, defaultValue: T): T {
        val value = pref.getString(key, defaultValue.toString()) ?: return defaultValue
        return when (T::class) {
            Boolean::class -> value.toBoolean() as T
            Float::class -> value.toFloat() as T
            Int::class -> value.toInt() as T
            Long::class -> value.toLong() as T
            String::class -> value as T
            Double::class -> value.toDouble() as T
            else -> {
                try {
                    GsonHelper.fromJson(value)
                } catch (e: Exception) {
                    defaultValue
                }
            }
        }
    }

    // support in java
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T> getPref(key: String, defaultValue: T, clsOfT: Class<T>): T {
        val value = pref.getString(key, defaultValue.toString()) ?: return defaultValue
        return when (clsOfT.simpleName) {
            "Boolean" -> value.toBoolean() as T
            "Float" -> value.toFloat() as T
            "Integer" -> value.toInt() as T
            "Long" -> value.toLong() as T
            "String" -> value as T
            "Double" -> value.toDouble() as T
            else -> {
                try {
                    GsonHelper.fromJson(value, clsOfT)
                } catch (e: Exception) {
                    defaultValue
                }
            }
        }
    }

    @JvmStatic
    fun <T> putPref(key: String, value: T) {
        with(pref.edit()) {
            putString(key, toStringValue(value))
            apply()
        }
    }

    @JvmStatic
    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    @JvmStatic
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun <T> toStringValue(value: T): String {
        return try {
            GsonHelper.toJson(value)
        } catch (e: Exception) {
            value.toString()
        }
    }
}