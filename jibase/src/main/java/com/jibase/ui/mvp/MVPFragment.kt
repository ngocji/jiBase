package com.jibase.ui.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.jibase.extensions.inflate

/**
 * Created by ngoc on 6/7/2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class MVPFragment<V : MVPView, P : MVPPresenter<V>>(@LayoutRes private val layoutResId: Int = -1) : Fragment(), MVPView {
    lateinit var mPresenter: P
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = initPresenter()
        return if (layoutResId > 0 && container != null) {
            container.inflate(layoutResId)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
        onViewListener()
    }

    override fun onDestroyView() {
        mPresenter.detach()
        super.onDestroyView()
    }

    abstract fun initPresenter(): P
}