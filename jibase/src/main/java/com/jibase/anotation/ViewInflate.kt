package com.jibase.anotation

import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewInflate(
        @LayoutRes val layout: Int = ID_NULL)