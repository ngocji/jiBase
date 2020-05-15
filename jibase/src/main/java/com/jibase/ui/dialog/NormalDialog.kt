package com.jibase.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import com.jibase.R
import com.jibase.anotation.BindingInfo
import com.jibase.anotation.BindingInfoHelper
import com.jibase.extensions.inflate

abstract class NormalDialog(@StyleRes private val style: Int = R.style.style_dialog_90) : DialogFragment() {
    open val bindingInfo: BindingInfo by lazy { BindingInfoHelper.getAnnotation(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, style)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(bindingInfo.layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        onViewReady(savedInstanceState)
        onViewListener()
    }


    abstract fun onViewReady(savedInstanceState: Bundle?)
    open fun onViewListener() {}
}