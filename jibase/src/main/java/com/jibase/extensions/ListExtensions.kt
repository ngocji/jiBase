@file:JvmName("ListExtensions")

package com.jibase.extensions


infix fun <T> List<T>.hasPosition(position: Int): Boolean = position in this.indices

fun <T> List<T>.addAll(items: List<T>, clearAll: Boolean = false): List<T> {
    val modifyList = if (clearAll) mutableListOf<T>() else toMutableList()
    modifyList.addAll(items)
    return modifyList
}
