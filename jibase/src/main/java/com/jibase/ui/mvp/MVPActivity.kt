package com.jibase.ui.mvp

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by ngoc on 6/7/2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class MVPActivity<V : MVPView, P : MVPPresenter<V>>(@LayoutRes private val layoutResId: Int = -1) : MVPView, AppCompatActivity() {
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutResId > 0) setContentView(layoutResId)
        mPresenter = initPresenter()
        onViewReady(savedInstanceState)
        onViewListener()
    }

    override fun onDestroy() {
        mPresenter.detach()
        super.onDestroy()
    }

    abstract fun initPresenter(): P
}