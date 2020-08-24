package com.jibase.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jibase.R
import com.jibase.anotation.InflateHelper
import com.jibase.anotation.ViewInflate

abstract class BaseBottomDialog : BottomSheetDialogFragment() {
    open val viewInflate: ViewInflate by lazy { InflateHelper.getAnnotation(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, initStyle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(viewInflate.layout, container, false)
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

    open fun initStyle(): Int {
        return R.style.style_dialog_100
    }

    open fun onViewReady(savedInstanceState: Bundle?) {}
    open fun onViewListener() {}

    fun show(fragmentManager: FragmentManager) {
        if (fragmentManager.findFragmentByTag(javaClass.name) != null) return
        show(fragmentManager, javaClass.name)
    }
}