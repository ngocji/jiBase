package com.jibase.ui.dialog

abstract class BaseBottomCallBackDialog<CallBack : Any> : BaseBottomDialog() {
    private var internalCallBack: CallBack? = null

    fun setCallBack(callback: CallBack): BaseBottomCallBackDialog<CallBack> {
        this.internalCallBack = callback
        return this
    }

    fun getCallBack(): CallBack? {
        return internalCallBack
    }
}