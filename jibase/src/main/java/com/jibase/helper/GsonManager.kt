package com.jibase.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jibase.helper.GsonManager.SIMPLE
import java.lang.reflect.Type

object GsonManager {
    const val SIMPLE = "simple"
    private val gsonMap by lazy { mutableMapOf<String, Gson>() }

    init {
        gsonMap[SIMPLE] = GsonBuilder().serializeSpecialFloatingPointValues().create()
    }

    @JvmStatic
    fun createGson(key: String, gson: Gson) {
        gsonMap[key] = gson
    }


    @JvmStatic
    fun <T> fromJson(data: String, type: Type): T? = fromJson(data, type, SIMPLE)

    @JvmStatic
    fun <T> fromJson(data: String): T? = fromJson(data, object : TypeToken<T>() {}.type)

    @JvmStatic
    fun <T> fromJson(data: String, gsonType: String): T? =
        fromJson(data, object : TypeToken<T>() {}.type, gsonType)

    @JvmStatic
    fun <T> fromJson(data: String, type: Type, gsonType: String = SIMPLE): T? {
        return try {
            gsonMap[gsonType]?.fromJson<T>(data, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun <T> toJson(data: T): String = toJson(data, SIMPLE)

    @JvmStatic
    fun <T> toJson(data: T, gsonType: String = SIMPLE): String {
        return try {
            gsonMap[gsonType]?.toJson(data) ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}

inline fun <reified T> fromJson(data: String, gsonType: String = SIMPLE): T? {
    return GsonManager.fromJson<T>(data, getTypeToken<T>(), gsonType)
}

// get type token from list
inline fun <reified T> getTypeToken(): Type = object : TypeToken<T>() {}.type
