package com.jibase.ui.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.jibase.extensions.destroy
import com.jibase.extensions.initBinding

abstract class BindNoViewModelActivity(
        @LayoutRes private val layoutResId: Int) : AppCompatActivity() {

    lateinit var binding: ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init binding
         binding = initBinding(layoutResId, null)

        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    open fun onViewListener() {
        // free implement
    }
    override fun onDestroy() {
        // Hacky : There's a memory leak issue with data binding if we don't set lifeCycleOwner to null
        binding.destroy()
        super.onDestroy()
    }
}