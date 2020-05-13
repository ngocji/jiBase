@file:JvmName("StringBuilderExtensions")

package com.jibase.extensions

fun StringBuilder.newString(string: String) {
    clear()
    append(string)
}

fun StringBuilder.clear() {
    setLength(0)
}

fun StringBuilder.replace(matcher: String, replace: String) {
    val stringReplace = toString().replace(matcher, replace)
    newString(stringReplace)
}