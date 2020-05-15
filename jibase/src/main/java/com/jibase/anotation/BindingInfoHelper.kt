package com.jibase.anotation

object BindingInfoHelper {
    fun getAnnotation(target: Any): BindingInfo {
        return target::class.annotations.find { it is BindingInfo } as? BindingInfo
            ?: throw IllegalArgumentException("You must use @BindInfo(...) to bind info into class")
    }
}