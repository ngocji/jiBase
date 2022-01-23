package com.jibase.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.fragment.app.Fragment
import com.jibase.anotation.InflateHelper
import com.jibase.anotation.ViewInflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    open val viewInflate: ViewInflate by lazy { InflateHelper.getAnnotation(this) }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initView(inflater, container)
    }

    open fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return if (this.viewInflate.layout != ID_NULL) {
            inflater.inflate(viewInflate.layout, container, false)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewListener()
        onViewReady(savedInstanceState)
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    open fun onViewListener() {
        // free implement
    }
}