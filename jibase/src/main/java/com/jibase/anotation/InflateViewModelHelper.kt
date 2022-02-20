package com.jibase.anotation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

object InflateViewModelHelper {
    fun inflate(target: Any) {
        if (target !is AppCompatActivity && target !is Fragment) throw IllformedLocaleException("Target must be fragment or activity")


    }



}