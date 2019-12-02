package com.jibase.helper.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified VM : ViewModel> getViewModelFromActivity(activity: FragmentActivity): VM {
    return ViewModelProvider(activity).get(VM::class.java)
}


inline fun <reified VM : ViewModel> ViewModelStoreOwner.getViewModel(): VM {
    return ViewModelProvider(this).get(VM::class.java)
}