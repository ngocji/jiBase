package com.jibase.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.databinding.ViewDataBinding
import com.jibase.R
import com.jibase.extensions.initBinding
import com.jibase.listener.OnDialogListener


open class BindBottomDialog<T>(@LayoutRes private val layoutRes: Int, private val data: T? = null, private val listener: OnDialogListener? = null, @StyleRes styleRes: Int = R.style.style_dialog_100) : NormalBottomDialog(layoutRes, styleRes) {
    lateinit var binding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = initBinding(container, layoutRes, data, listener)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
    }

    override fun onViewListener() {
    }
}
