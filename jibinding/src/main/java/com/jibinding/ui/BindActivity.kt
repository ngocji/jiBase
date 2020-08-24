package com.jibinding.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.jibase.extensions.destroy
import com.jibase.ui.mvvm.BindViewModel

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BindActivity<VM : BindViewModel> : BaseActivity<VM>() {
    lateinit var binding: ViewDataBinding

    override fun initView(savedInstanceState: Bundle?) {
        binding = initBinding(viewInflate.layout, viewModel)

        onViewReady(savedInstanceState)
        onViewListener()
    }

    override fun onDestroy() {
        // Hacky : There's a memory leak issue with data binding if we don't set lifeCycleOwner to null
        binding.destroy()
        super.onDestroy()
    }
}

typealias SimpleBindActivity = BindActivity<BindViewModel>