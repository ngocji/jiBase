package com.jibase.ui.dialog

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jibase.ui.DialogCallBackStore

abstract class BaseCallBackDialog<CallBack> : BaseDialog() {
    private val callbackStore: DialogCallBackStore<CallBack> by lazy {
        ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory())
                .get(DialogCallBackStore::class.java) as DialogCallBackStore<CallBack>
    }

    private var tempCallBack: CallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (callbackStore.callback == null) {
            callbackStore.callback = tempCallBack
        }
    }

    fun setCallBack(callback: CallBack) {
        tempCallBack = callback
    }

    fun getCallBack(): CallBack? {
        return callbackStore.callback
    }
}