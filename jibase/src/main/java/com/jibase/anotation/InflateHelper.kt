package com.jibase.anotation

object InflateHelper {
    fun getAnnotation(target: Any): Inflate {
        return target::class.annotations.find { it is Inflate } as? Inflate
            ?: throw IllegalArgumentException("You must use @Inflate(...) to bind info into class")
    }
}