package com.jibase.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.jibase.utils.RuntimeTypeAdapterFactory
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Create by Ngocji on 11/12/2018
 **/

class GsonHelper {
    var mGson: Gson? = null

    init {
        mGson = createNewGson<Any>()
    }

    fun setCustomGson(gson: Gson) {
        mGson = gson
    }

    fun <T> fromJsonList(data: String, type: Type): T {
        return mGson?.fromJson(data, type) ?: throw Exception("Gson can't convert")
    }

    fun <T> toJson(data: T): String {
        return mGson?.toJson(data) ?: ""
    }


    // copy entity
    inline fun <reified T> copy(data: T): T {
        val encode = toJson(data)
        return fromJson(encode)
    }

    // parse string to list
    inline fun <reified T> fromJsonList(data: String): List<T> {
        val type = getTypeToken<T>()
        return mGson?.fromJson(data, type) ?: throw Exception("Gson can't convert")
    }


    // parse string to data
    inline fun <reified T> fromJson(data: String): T {
        return mGson?.fromJson(data, T::class.java) ?: throw Exception("Gson can't convert")
    }

    // get type token from list
    inline fun <reified T> getTypeToken(): Type = object : TypeToken<T>() {}.type

    // create adapter factory
    inline fun <reified T : Any> createAdapterFactory(vararg cls: KClass<out T>): TypeAdapterFactory {
        val adapter = RuntimeTypeAdapterFactory.of(T::class.java)
        cls.forEach {
            adapter.registerSubtype(it.java)
        }
        return adapter
    }


    // create new gson
    inline fun <reified T : Any> createNewGson(vararg cls: KClass<out T>): Gson {
        val builder = GsonBuilder().serializeSpecialFloatingPointValues()
        builder.registerTypeAdapterFactory(createAdapterFactory(*cls))
        return builder.create()
    }
}