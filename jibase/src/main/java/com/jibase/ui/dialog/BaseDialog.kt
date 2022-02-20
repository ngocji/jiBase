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
import com.jibase.R
import com.jibase.anotation.InflateFactory

abstract class BaseDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, initStyle())
        InflateFactory.run(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return InflateFactory.getViewBinding(this)?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }

    open fun initStyle(): Int {
        return R.style.style_dialog_90
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    fun show(fragmentManager: FragmentManager) {
        if (fragmentManager.findFragmentByTag(javaClass.name) != null) return
        show(fragmentManager, javaClass.name)
    }
}