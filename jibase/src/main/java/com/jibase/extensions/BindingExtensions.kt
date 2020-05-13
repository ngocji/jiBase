@file:JvmName("BindingExtensions")

package com.jibase.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.jibase.BR
import com.jibase.listener.OnDialogListener

fun <VM : ViewModel> Fragment.initBinding(
    layoutResId: Int,
    inflater: LayoutInflater,
    container: ViewGroup?,
    vm: VM?
): ViewDataBinding {
    return DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutResId, container, false).apply {
        lifecycleOwner = this@initBinding
        setVariable(BR.vm, vm)
        setVariable(BR.listener, this@initBinding)
    }
}

fun <VM : ViewModel> AppCompatActivity.initBinding(layoutResId: Int, vm: VM?): ViewDataBinding {
    return DataBindingUtil.setContentView<ViewDataBinding>(this, layoutResId).apply {
        lifecycleOwner = this@initBinding
        setVariable(BR.vm, vm)
        setVariable(BR.listener, this@initBinding)
    }
}


fun Fragment.initBinding(
    container: ViewGroup?,
    layoutResId: Int,
    data: Any?,
    listener: OnDialogListener?
): ViewDataBinding {
    return DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(context),
        layoutResId,
        container,
        false
    ).apply {
        data?.also { setVariable(BR.data, it) }
        setVariable(BR.listener, listener ?: this@initBinding)
    }
}

fun ViewGroup.initBinding(layoutResId: Int): ViewDataBinding {
    return DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(this.context),
        layoutResId,
        this,
        false
    )
}

fun ViewDataBinding.bind(data: Any?) {
    setVariable(BR.data, data)
}

fun ViewDataBinding.destroy() {
    lifecycleOwner = null
    unbind()
}
