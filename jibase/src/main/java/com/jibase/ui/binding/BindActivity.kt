package com.jibase.ui.binding

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.jibase.extensions.destroy
import com.jibase.extensions.initBinding
import com.jibase.ui.BindViewModel
import com.jibase.ui.base.BaseActivity

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BindActivity<VM : BindViewModel> : BaseActivity<VM>() {
    lateinit var binding: ViewDataBinding

    override fun initView(savedInstanceState: Bundle?) {
        binding = initBinding(inflate.layout, viewModel)

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