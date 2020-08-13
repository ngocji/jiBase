package com.jibase.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.fragment.app.Fragment
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateHelper
import com.jibase.extensions.inflate
import com.jibase.ui.BindViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BaseFragment<VM : BindViewModel> : Fragment() {
    open val inflate: Inflate by lazy { InflateHelper.getAnnotation(this) }
    open val viewModel: VM by viewModel(inflate.viewModel as KClass<VM>)

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container)
    }

    open fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return if (this.inflate.layout != ID_NULL) {
            container?.inflate(inflate.layout)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    open fun onViewListener() {
        // free implement
    }
}

typealias SimpleBaseFragment = BaseFragment<BindViewModel>