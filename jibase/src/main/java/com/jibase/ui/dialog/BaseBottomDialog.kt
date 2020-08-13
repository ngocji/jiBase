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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jibase.R
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateHelper

abstract class BaseBottomDialog(@StyleRes private val styleRes: Int = R.style.style_dialog_100) : BottomSheetDialogFragment() {
    open val inflate: Inflate by lazy { InflateHelper.getAnnotation(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, styleRes)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(inflate.layout, container, false)
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


    open fun onViewReady(savedInstanceState: Bundle?) {}
    open fun onViewListener() {}
}