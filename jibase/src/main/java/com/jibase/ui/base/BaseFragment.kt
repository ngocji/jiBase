package com.jibase.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jibase.anotation.InflateFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        InflateFactory.run(this)
        return initView(inflater, container)
    }

    open fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return InflateFactory.getViewBinding(this)?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)
}