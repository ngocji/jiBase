package com.jibase.ui.mvvm

import androidx.lifecycle.ViewModel
import com.jibase.BaseApp

abstract class BindViewModel : ViewModel() {
    fun getApplicationContext() = BaseApp.instance
}