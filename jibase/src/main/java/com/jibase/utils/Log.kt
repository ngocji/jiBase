package com.jibase.utils

import com.jibase.BuildConfig
import kotlin.math.max

object Log {
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
            createMessage(message).forEach {
                android.util.Log.e(prefix, it)
            }
        }
    }

    @JvmStatic
    fun d(message: String, prefix: String = this.prefix) {
        if (isEnable) {
            createMessage(message).forEach {
                android.util.Log.d(prefix, it)
            }
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

    private fun createMessage(string: String): List<String> {
        return listOf(
            "********************************",
            "Class: ${getClassName()} (${getMethodName()} : ${getLineNumber()})",
            "$string",
            "********************************"
        )
    }

    private fun getClassName(): String {
        val fileName = Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].fileName
        val index = fileName.lastIndexOf(".")
        return if (fileName.isNotBlank()) fileName.substring(
            0,
            max(index, fileName.length - 1)
        ) else ""
    }

    private fun getMethodName(): String {
        return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].methodName
    }

    private fun getLineNumber(): String {
        return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].lineNumber.toString()
    }
}