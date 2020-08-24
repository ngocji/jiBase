package com.jibase.ui

import androidx.lifecycle.ViewModel

class DialogCallBackStore<Callback> : ViewModel() {
    var callback: Callback? = null
}