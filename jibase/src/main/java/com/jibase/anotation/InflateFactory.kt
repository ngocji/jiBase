package com.jibase.anotation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jibase.utils.ViewBindingUtils
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

object InflateFactory {
    fun run(target: Any) {
        target::class.declaredMemberProperties.forEach { kProperty ->
            // return access filed
            kProperty.isAccessible = true
            if (kProperty is KMutableProperty<*>) {
                val javaField = kProperty.javaField ?: return@forEach
                val instance = when {

                    javaField.isAnnotationPresent(InflateViewModel::class.java) -> {
                        createViewModel(
                            target,
                            javaField.type as Class<out ViewModel>
                        )
                    }

                    javaField.isAnnotationPresent(InflateActivityViewModel::class.java) -> {
                        createActivityViewModel(
                            target,
                            javaField.type as Class<out ViewModel>
                        )
                    }

                    javaField.isAnnotationPresent(Inflate::class.java) -> {
                        createViewBinding(
                            target, javaField.type as Class<out ViewBinding>
                        )
                    }

                    else -> return@forEach
                }

                kProperty.setter.call(target, instance)
            }
        }
    }


    fun getViewBinding(target: Any): ViewBinding? {
        val kProperty = target::class.declaredMemberProperties.find { kProperty ->
            kProperty.javaField?.isAnnotationPresent(Inflate::class.java) ?: false
        }

        return if (kProperty != null) {
            kProperty.isAccessible = true
            kProperty.javaField?.get(target) as? ViewBinding
        } else {
            null
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

    private fun createViewBinding(target: Any, clazz: Class<out ViewBinding>): ViewBinding {
        return when (target) {
            is Fragment -> {
                ViewBindingUtils.inflate(target.layoutInflater, clazz)
            }

            is AppCompatActivity -> {
                ViewBindingUtils.inflate(target.layoutInflater, clazz)
            }

            else -> throw NullPointerException("Error inflate viewBinding")
        }
    }
}