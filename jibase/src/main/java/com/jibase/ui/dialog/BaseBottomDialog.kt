package com.jibase.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jibase.R
import com.jibase.anotation.InflateHelper
import com.jibase.anotation.ViewInflate
import com.jibase.ui.DialogStore

abstract class BaseBottomDialog : BottomSheetDialogFragment() {
    open val viewInflate: ViewInflate by lazy { InflateHelper.getAnnotation(this) }
    open val dialogStore: DialogStore by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DialogStore::class.java)
    }

    private val tempProperties = hashMapOf<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, initStyle())
        dialogStore.add(tempProperties)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (viewInflate.layout != ID_NULL) {
            inflater.inflate(viewInflate.layout, container, false)
        } else {
            null
        }
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

    fun <T> addProperty(key: String, data: T) {
        if (data != null) {
            tempProperties[key] = data
        } else {
            tempProperties.remove(key)
        }
    }

    fun <T> getProperty(key: String): T? {
        return dialogStore.get(key)
    }
}