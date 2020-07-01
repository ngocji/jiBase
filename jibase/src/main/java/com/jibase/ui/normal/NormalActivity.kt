package com.jibase.ui.normal

import android.content.res.Resources.ID_NULL
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateHelper

abstract class NormalActivity : AppCompatActivity() {
    open val inflate: Inflate by lazy { InflateHelper.getAnnotation(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (inflate.layout != ID_NULL) {
            setContentView(inflate.layout)
        }
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    open fun onViewListener() {}
}