package com.jibase.anotation

object InflateHelper {
    fun getViewInflate(target: Any): ViewInflate {
        return findAnnotation(target)
    }

    inline fun <reified T : Annotation> findAnnotation(target: Any): T {
        return target::class.annotations.find { it is T } as? T
            ?: throw IllegalArgumentException("Not found annotation")
    }
}