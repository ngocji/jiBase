package com.jibase.ui.normal

import android.content.res.Resources.ID_NULL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jibase.anotation.BindingInfo
import com.jibase.anotation.BindingInfoHelper
import com.jibase.extensions.inflate


abstract class NormalFragment : Fragment() {
    open val bindingInfo: BindingInfo by lazy { BindingInfoHelper.getAnnotation(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (bindingInfo.layout != ID_NULL) container?.inflate(bindingInfo.layout) else null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    open fun onViewListener() {}
}