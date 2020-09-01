package com.jibase.ui.dialog

abstract class BaseBottomCallBackDialog<CallBack : Any> : BaseBottomDialog() {

    fun setCallBack(callback: CallBack) {
        addProperty("callback", callback)
    }

    fun getCallBack(): CallBack? {
        return getProperty("callback")
    }
}