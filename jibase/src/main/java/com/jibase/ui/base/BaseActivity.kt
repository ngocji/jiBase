package com.jibase.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.anotation.InflateFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InflateFactory.run(this)
        initView(savedInstanceState)
    }

    open fun initView(savedInstanceState: Bundle?) {
        InflateFactory.getViewBinding(this)?.also {
            setContentView(it.root)
        }

        onViewReady(savedInstanceState)
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
}