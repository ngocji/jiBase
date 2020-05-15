package com.jibase.anotation

import androidx.annotation.LayoutRes
import com.jibase.ui.mvvm.BindViewModel
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention()
@MustBeDocumented
@Inherited
annotation class BindingInfo(@LayoutRes val layout: Int,
                             val viewModel: KClass<*> = BindViewModel::class)