package com.jibase.ui.normal

import android.content.res.Resources.ID_NULL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateHelper
import com.jibase.extensions.inflate


abstract class NormalFragment : Fragment() {
    open val inflate: Inflate by lazy { InflateHelper.getAnnotation(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (inflate.layout != ID_NULL) container?.inflate(inflate.layout) else null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
        onViewListener()
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
    open fun onViewListener() {}
}