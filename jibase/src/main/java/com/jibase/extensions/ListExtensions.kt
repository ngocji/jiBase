package com.jibase.extensions

import com.jibase.entities.base.EntityData
import com.jibase.helper.gson.GsonHelper
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Check position in list
 * @return true / false
 */
infix fun <T> List<T>.has(position: Int): Boolean {
    return position in this.indices
}

/**
 * Check string in list string
 */
infix fun List<String>.has(key: String): Boolean {
    if (toString().contains(key)) {
        return true
    }
    return false
}

/**
 * Check object in list object
 * @return true/false
 */
infix fun <T> List<T>.has(data: T): Boolean {
    return this.indexOf(data) != -1
}

/**
 * Get item of position
 * @param position
 * @return item or null
 */

fun <T> List<T>.getOrNull(position: Int): T? {
    return if (this has position) this[position]
    else null
}

/**
 * Make for down
 */
inline fun <T> List<T>.forDownIndexed(action: (index: Int, T) -> Unit) {
    for (i in (this.size - 1) downTo 0) action(i, this[i])
}

/**
 * Make copy list
 * @param src: list original need copy
 * @param out: list out when copy
 * @return out list
 */
inline fun <reified T : EntityData> copyList(src: List<T>, out: MutableList<T>) {
    val temp = src.map { GsonHelper.copy(it) }
    out.addNeedClear(temp)
}

/**
 * Make cloneable list
 */
fun <T> cloneList(src: List<T>, type: Type): List<T> {
    return Gson().fromJson(Gson().toJson(src), type)
}

/**
 * Make remove old data and copy new data
 * @param list: list use replace
 */
fun <T> MutableList<T>.addNeedClear(list: List<T>) {
    clear()
    addAll(list)
}

fun <T> MutableList<T>.addNeedClear(list: Array<T>) {
    clear()
    addAll(list)
}

fun <T> MutableList<T>.addNeedClear(item: T) {
    clear()
    add(item)
}


fun <T, V> MutableMap<T, V>.addNeedClear(key: T, data: V) {
    clear()
    put(key, data)
}

/**
 * Get key of hashmap not null
 * @param key: key of hashmap
 * @param default: default value when not exists key
 * @return V: data
 */

fun <T, V> HashMap<T, V>.getNotNull(key: T, default: V): V {
    val item = get(key)
    return if (item != null) {
        item
    } else {
        put(key, default)
        default
    }
}

/**
 * get first entry of map]
 */

fun <T, V> Map<T, V>.first(): Map.Entry<T, V>? {
    forEach {
        return it
    }
    return null
}



