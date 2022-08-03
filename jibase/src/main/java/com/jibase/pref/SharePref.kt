package com.jibase.pref

import android.content.Context
import android.content.SharedPreferences
import com.jibase.helper.GsonManager
import com.jibase.utils.Log
import java.lang.reflect.Type

class SharePref(context: Context, prefName: String) {
    val pref: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    inline fun <reified T> get(key: String) = get(key, null, T::class.java)

    inline fun <reified T> get(key: String, defaultValue: T) =
        get(key, defaultValue, T::class.java) ?: defaultValue

    fun <T> get(key: String, clazz: Class<T>): T? = get(key, null, clazz)

    fun <T> get(key: String, defaultValue: T?, clazz: Class<out T>): T? {
        val value = pref.getString(key, defaultValue.toString()) ?: return defaultValue
        return when (clazz.canonicalName) {
            Boolean::class.java.canonicalName -> value.toBoolean() as T
            Float::class.java.canonicalName -> value.toFloat() as T
            Int::class.java.canonicalName -> value.toInt() as T
            Long::class.java.canonicalName -> value.toLong() as T
            String::class.java.canonicalName -> value as T
            Double::class.java.canonicalName -> value.toDouble() as T
            else -> defaultValue
        }
    }

    // region get for java

    fun getBoolean(key: String, defaultValue: Boolean) = get(key, defaultValue, Boolean::class.java)
    fun getLong(key: String, defaultValue: Long) = get(key, defaultValue, Long::class.java)
    fun getFloat(key: String, defaultValue: Float) = get(key, defaultValue, Float::class.java)
    fun getDouble(key: String, defaultValue: Double) = get(key, defaultValue, Double::class.java)
    fun getString(key: String, defaultValue: String) = get(key, defaultValue, String::class.java)
    fun getInt(key: String, defaultValue: Int) = get(key, defaultValue, Int::class.java)

    fun <T> getObject(key: String, defaultValue: T?, type: Type): T? {
        val content = get(key, "", String::class.java)
        return try {
            if (content.isNullOrBlank()) {
                defaultValue
            } else {
                GsonManager.fromJson(content, type)
            }
        } catch (e: Exception) {
            defaultValue
        }
    }


    fun putBoolean(key: String, value: Boolean) {
        pref.edit().putString(key, value.toString()).apply()
    }

    fun putLong(key: String, value: Long) {
        pref.edit().putString(key, value.toString()).apply()
    }

    fun putFloat(key: String, value: Float) {
        pref.edit().putString(key, value.toString()).apply()
    }

    fun putDouble(key: String, value: Double) {
        pref.edit().putString(key, value.toString()).apply()
    }

    fun putString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        pref.edit().putString(key, value.toString()).apply()
    }

    fun <T> putObject(key: String, value: T?) {
        if (value == null) {
            pref.edit().remove(key).apply()
        } else {
            putString(key, GsonManager.toJson(value))
        }
    }

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