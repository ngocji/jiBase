package com.jibase.ui.normal

import android.content.res.Resources.ID_NULL
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class NormalActivity(@LayoutRes private val layoutRes: Int = ID_NULL) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutRes != ID_NULL) {
            setContentView(layoutRes)
        }
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    abstract fun onViewListener()
}