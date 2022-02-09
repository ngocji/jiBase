package com.jibase.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.ID_NULL
import com.jibase.anotation.InflateHelper
import com.jibase.anotation.InflateViewModelHelper
import com.jibase.anotation.ViewInflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    open val viewInflate: ViewInflate by lazy { InflateHelper.getViewInflate(this) }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InflateViewModelHelper.inflate(this)
        initView(savedInstanceState)
    }

    open fun initView(savedInstanceState: Bundle?) {
        if (viewInflate.layout != ID_NULL) {
            setContentView(viewInflate.layout)
        }

        onViewListener()
        onViewReady(savedInstanceState)
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    open fun onViewListener() {
        // free implement
    }
}