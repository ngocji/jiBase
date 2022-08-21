package com.jibase.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.jibase.R

abstract class BaseDialog(@LayoutRes layoutId: Int = 0) : DialogFragment(layoutId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, initStyle())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.run {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }

    open fun initStyle(): Int {
        return R.style.style_dialog_90
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    fun show(fragmentManager: FragmentManager): Boolean {
        try {
            if (fragmentManager.findFragmentByTag(javaClass.name) != null) return false
            show(fragmentManager, javaClass.name)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}