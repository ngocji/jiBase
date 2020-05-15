package com.jibase.utils

import android.util.Log
import com.jibase.BuildConfig
import org.koin.core.KoinComponent

object Log : KoinComponent {
    var prefix = "MY_LOG"
    var isEnable = BuildConfig.DEBUG

    private const val STACK_TRACE_LEVELS_UP = 6

    @JvmStatic
    fun setup(prefix: String, isEnable: Boolean) {
        this.prefix = prefix
        this.isEnable = isEnable
    }

    @JvmStatic
    fun e(message: String, prefix: String = this.prefix) {
        if (isEnable) {
            Log.e(prefix, createMessage(message))
        }
    }

    @JvmStatic
    fun d(message: String, prefix: String = this.prefix) {
        if (isEnable) {
            Log.d(prefix, createMessage(message))
        }
    }

    @JvmStatic
    fun e(message: String) {
        e(message, prefix)
    }

    @JvmStatic
    fun d(message: String) {
        d(message, prefix)
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