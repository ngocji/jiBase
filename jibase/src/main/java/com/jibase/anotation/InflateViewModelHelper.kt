package com.jibase.anotation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

object InflateViewModelHelper {
    fun inflate(target: Any) {
        if (target !is AppCompatActivity && target !is Fragment) throw IllformedLocaleException("Target must be fragment or activity")

        target::class.declaredMemberProperties.forEach { kProperty ->
            if (kProperty is KMutableProperty<*>) {
                val javaField = kProperty.javaField ?: return@forEach
                val instanceViewModel = when {

                    javaField.isAnnotationPresent(InflateViewModel::class.java) -> {
                        createViewModel(target, javaField.type as Class<out ViewModel>)
                    }

                    javaField.isAnnotationPresent(InflateActivityViewModel::class.java) -> {
                        createActivityViewModel(
                            target,
                            javaField.type as Class<out ViewModel>
                        )
                    }

                    else -> return@forEach
                }

                kProperty.setter.call(target, instanceViewModel)
            }
        }
    }


    private fun <T : ViewModel> createViewModel(target: Any, clazz: Class<T>): ViewModel {
        return when (target) {
            is Fragment -> {
                ViewModelProvider(
                    target,
                    ViewModelProvider.NewInstanceFactory()
                ).get(clazz)
            }

            is AppCompatActivity -> {
                ViewModelProvider(
                    target,
                    ViewModelProvider.NewInstanceFactory()
                ).get(clazz)
            }

            else -> throw NullPointerException("Error inflate viewModel")
        }
    }

    private fun <T : ViewModel> createActivityViewModel(target: Any, clazz: Class<T>): T {
        return when (target) {
            is Fragment -> {
                ViewModelProvider(
                    target.requireActivity(),
                    ViewModelProvider.NewInstanceFactory()
                ).get(clazz)
            }

            is AppCompatActivity -> {
                ViewModelProvider(
                    target,
                    ViewModelProvider.NewInstanceFactory()
                ).get(clazz)
            }

            else -> throw NullPointerException("Error inflate viewModel")
        }
    }
}