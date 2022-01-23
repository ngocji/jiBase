package com.jibase.ui.dialog

abstract class BaseCallBackDialog<CallBack : Any> : BaseDialog() {
    private var internalCallBack: CallBack? = null

    fun setCallBack(callback: CallBack): BaseCallBackDialog<CallBack> {
        this.internalCallBack = callback
        return this
    }

    fun getCallBack(): CallBack? {
        return internalCallBack
    }
}