package com.jibase.ui.dialog

import androidx.annotation.LayoutRes

abstract class BaseCallBackDialog<CallBack : Any>(@LayoutRes layoutId:Int = 0) : BaseDialog(layoutId) {
    private var internalCallBack: CallBack? = null

    fun setCallBack(callback: CallBack): BaseCallBackDialog<CallBack> {
        this.internalCallBack = callback
        return this
    }

    fun getCallBack(): CallBack? {
        return internalCallBack
    }
}