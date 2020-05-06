package com.jibase.extensions

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
 * Make for down
 */
inline fun <T> List<T>.forDownIndexed(action: (index: Int, T) -> Unit) {
    for (i in (this.size - 1) downTo 0) action(i, this[i])
}

/**
 * Make remove old data and copy new data
 * @param list: list use replace
 */
fun <T> MutableList<T>.addNeedClear(list: List<T>) {
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

fun <T, V> Map<T, V>.firstOrNull(): Map.Entry<T, V>? {
    forEach {
        return it
    }
    return null
}



