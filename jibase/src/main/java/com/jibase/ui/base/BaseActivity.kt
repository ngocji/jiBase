package com.jibase.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.lifecycle.ViewModelProvider
import com.jibase.anotation.InflateHelper
import com.jibase.anotation.ViewInflate
import com.jibase.ui.BaseViewModel
import io.reactivex.disposables.Disposable

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    open val viewInflate: ViewInflate by lazy { InflateHelper.getAnnotation(this) }
    open val viewModel: VM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(viewInflate.viewModel.java) as VM
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    fun Disposable.putToComposite() {
        viewModel.compositeDisposable.add(this)
    }
}

typealias SimpleBaseActivity = BaseActivity<BaseViewModel>