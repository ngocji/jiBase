package com.jibase.ui.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.jibase.extensions.destroy
import com.jibase.extensions.initBinding
import com.jibase.extensions.getViewModel

abstract class BindActivity<VM : BindViewModel>(
    @LayoutRes private val layoutResId: Int,
    private val clazzViewModel: Class<VM>
) : AppCompatActivity() {

    open val viewModel: VM by lazy { getViewModel(clazzViewModel) }

    lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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