package com.jibase.ui.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.jibase.anotation.BindingInfo
import com.jibase.anotation.BindingInfoHelper
import com.jibase.extensions.destroy
import com.jibase.extensions.initBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BindActivity<VM : BindViewModel> : AppCompatActivity() {
    open val bindingInfo: BindingInfo by lazy { BindingInfoHelper.getAnnotation(this) }
    open val viewModel by viewModel(bindingInfo.viewModel as KClass<VM>)

    lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init binding
        binding = initBinding(bindingInfo.layout, viewModel)

        onViewReady(savedInstanceState)
        onViewListener()

        BindActivity::class.java.methods.first().annotations
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