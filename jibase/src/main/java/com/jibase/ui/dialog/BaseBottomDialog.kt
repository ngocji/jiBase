package com.jibase.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jibase.R

abstract class BaseBottomDialog(
    @LayoutRes private val layoutId: Int,
    @StyleRes
    private val theme: Int = 0
) : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutId > 0) {
            val contextThemeWrapper = ContextThemeWrapper(context, theme)
            inflater.cloneInContext(contextThemeWrapper).inflate(layoutId, container, false)
        } else {
            null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.run {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if (isShowFullDialog()) {
                setOnShowListener { dialog ->
                    val d = dialog as BottomSheetDialog

                    val bottomSheet =
                        d.findViewById<View>(R.id.design_bottom_sheet) ?: return@setOnShowListener

                    BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }

    open fun setStyle() {
        setStyle(STYLE_NO_TITLE, initStyle())
    }

    open fun initStyle(): Int {
        return R.style.style_dialog_100
    }

    open fun isShowFullDialog(): Boolean = false

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