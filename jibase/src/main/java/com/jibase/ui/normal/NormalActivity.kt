package com.jibase.ui.normal

import android.content.res.Resources.ID_NULL
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.anotation.BindingInfo
import com.jibase.anotation.BindingInfoHelper

abstract class NormalActivity : AppCompatActivity() {
    open val bindingInfo: BindingInfo by lazy { BindingInfoHelper.getAnnotation(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (bindingInfo.layout != ID_NULL) {
            setContentView(bindingInfo.layout)
        }
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    abstract fun onViewListener()
}