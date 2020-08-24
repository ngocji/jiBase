package com.jibase.anotation

object InflateHelper {
    fun getAnnotation(target: Any): ViewInflate {
        return target::class.annotations.find { it is ViewInflate } as? ViewInflate
            ?: throw IllegalArgumentException("You must use @Inflate(...) to bind info into class")
    }
}