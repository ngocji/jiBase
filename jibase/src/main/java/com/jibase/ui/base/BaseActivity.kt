package com.jibase.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.ID_NULL
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateHelper
import com.jibase.ui.BindViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BaseActivity<VM : BindViewModel> : AppCompatActivity() {
    open val inflate: Inflate by lazy { InflateHelper.getAnnotation(this) }
    open val viewModel by viewModel(inflate.viewModel as KClass<VM>)

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
    }

    open fun initView(savedInstanceState: Bundle?) {
        if (inflate.layout != ID_NULL) {
            setContentView(inflate.layout)
        }

        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    open fun onViewListener() {
        // free implement
    }
}

typealias SimpleBaseActivity = BaseActivity<BindViewModel>