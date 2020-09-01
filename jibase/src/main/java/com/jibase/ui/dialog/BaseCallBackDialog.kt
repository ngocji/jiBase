package com.jibase.ui.dialog

abstract class BaseCallBackDialog<CallBack : Any> : BaseDialog() {

    fun setCallBack(callback: CallBack) {
        addProperty("callback", callback)
    }

    fun getCallBack(): CallBack? {
        return getProperty("callback")
    }
}