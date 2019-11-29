package com.jibase.ui.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.jibase.extensions.destroy
import com.jibase.extensions.initBinding

abstract class BindActivity<VM : BindViewModel>(
        @LayoutRes private val layoutResId: Int,
        private val clazzViewModel: Class<VM>) : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create viewModel
        viewModel = ViewModelProviders.of(this).get(clazzViewModel)

        // init binding
        binding = initBinding(layoutResId, viewModel)

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