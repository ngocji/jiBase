package com.jibinding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.databinding.ViewDataBinding
import com.jibase.R

open class BindDialog<T>(private val data: T? = null, private val listener: Any? = null, @StyleRes styleRes: Int = R.style.style_dialog_90) : BaseDialog(styleRes) {
    lateinit var binding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = initBinding(container, viewInflate.layout, data, listener)
        return binding.root
    }
}
