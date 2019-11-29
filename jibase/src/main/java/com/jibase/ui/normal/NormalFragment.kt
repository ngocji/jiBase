package com.jibase.ui.normal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.jibase.extensions.inflate

abstract class NormalFragment(@LayoutRes private val layoutResId: Int = -1) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(layoutResId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady()
        onViewListener()
    }

    abstract fun onViewReady()
    abstract fun onViewListener()
}