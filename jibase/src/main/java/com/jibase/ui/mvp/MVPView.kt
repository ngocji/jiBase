package com.jibase.ui.mvp

import android.os.Bundle

interface MVPView {
    fun onViewReady(savedInstanceState: Bundle?)
    fun onViewListener()
}