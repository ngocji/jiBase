package com.jibase.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

object ViewModelHelper {
    @JvmStatic
    fun <T : ViewModelStoreOwner, VM : ViewModel> newViewModel(target: T, clazz: Class<VM>): VM {
        return ViewModelProvider(target).get(clazz)
    }
}