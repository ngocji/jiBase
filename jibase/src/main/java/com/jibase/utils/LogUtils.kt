package com.jibase.utils

import android.util.Log
import com.jibase.BaseApp
import java.lang.Exception

private const val STACK_TRACE_LEVELS_UP = 6

fun Exception.print() {
    log(toString(), "EXCEPTIONS")
}

fun log(string: String, prefix: String = "MY_LOG") {
    if (BaseApp.instance.isEnableLog) {
        Log.e(prefix, createMessage(string))
    }
}

fun logd(string: String, prefix: String = "MY_LOG") {
    if (BaseApp.instance.isEnableLog) {
        Log.d(prefix, createMessage(string))
    }
}

private fun createMessage(string: String): String {
    return "Class: ${getClassName()}\nMethod: ${getMethodName()}\nLine: ${getLineNumber()})\nMessage: $string"
}

private fun getClassName(): String {
    val fileName = Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].fileName
    // Removing ".kt" and returning class name
    return fileName.substring(0, fileName.length - 3)
}

private fun getMethodName(): String {
    return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].methodName
}

private fun getLineNumber(): String {
    return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].lineNumber.toString()
}