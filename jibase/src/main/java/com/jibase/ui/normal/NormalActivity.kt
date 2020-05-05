package com.jibase.ui.normal

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class NormalActivity(@LayoutRes private val layoutRes: Int = -1) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutRes > 0) setContentView(layoutRes)
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    abstract fun onViewListener()
}