package com.jibase.helper.gson

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

object GsonHelper {
    var mGson: Gson? = null

    fun setOrCreateGson(gson: Gson? = null): Gson {
        return if (gson != null) {
            mGson = gson
            gson
        } else {
            mGson ?: createGsonBuilder().create().apply {
                mGson = this
            }
        }
    }

    fun createGsonBuilder(): GsonBuilder {
        return GsonBuilder().serializeSpecialFloatingPointValues()
    }

    fun <T> fromJsonList(data: String, type: Type, adapter: TypeAdapterFactory? = null): T {
        val gson = createGsonBuilder()
        adapter?.also { gson.setPrettyPrinting().registerTypeAdapterFactory(it) }
        return gson.create().fromJson(data, type)
    }

    fun <T> toJson(data: T): String {
        return setOrCreateGson().toJson(data)
    }

    fun <T> toJsonList(data: T, adapter: TypeAdapterFactory): String {
        val gson = createGsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create()
        return gson.toJson(data)
    }


    /**           inline method            **/

    /**
     * Copy entity
     */
    inline fun <reified T> copy(data: T): T {
        val encode = toJson(data)
        return fromJson(encode)
    }

    /**
     * parser string to list
     */
    inline fun <reified T> fromJsonList(data: T, adapter: TypeAdapterFactory): T {
        return fromJsonList(toJsonList(data, adapter), getTypeToken<T>(), adapter)
    }

    inline fun <reified T, reified K : Any> fromJsonList(data: T, arr: Array<KClass<K>>): T {
        val adapter = createAdapterFactory(*arr)
        return fromJsonList(toJsonList(data, adapter), getTypeToken<T>(), adapter) as T
    }

    inline fun <reified T> fromJsonList(data: T): T {
        return fromJsonList(toJson(data), getTypeToken<T>())
    }


    /**
     * parser string to object
     */
    inline fun <reified T> fromJson(data: String): T {
        return setOrCreateGson().fromJson(data, T::class.java)
    }

    /**
     *  get type token
     */
    inline fun <reified T> getTypeToken() = object : TypeToken<T>() {}.type

    /**
     * get adapterFactory with interface class
     */
    inline fun <reified T : Any> createAdapterFactory(vararg cls: KClass<out T>): TypeAdapterFactory {
        val adapter = RuntimeTypeAdapterFactory.of(T::class.java)
        cls.forEach {
            adapter.registerSubtype(it.java)
        }
        return adapter
    }
}