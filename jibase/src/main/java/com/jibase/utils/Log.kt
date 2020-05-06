package com.jibase.utils

import android.util.Log
import com.jibase.BuildConfig
import org.koin.core.KoinComponent

object Log : KoinComponent {
    private var isEnable = BuildConfig.DEBUG
    private const val STACK_TRACE_LEVELS_UP = 6

    fun e(string: String, prefix: String = "MY_LOG") {
        if (isEnable) {
            Log.e(prefix, createMessage(string))
        }
    }

    fun d(string: String, prefix: String = "MY_LOG") {
        if (isEnable) {
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
}