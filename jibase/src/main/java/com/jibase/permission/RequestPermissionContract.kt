package com.jibase.permission

import android.app.Activity
import com.jibase.ui.mvp.MVPPresenter
import com.jibase.ui.mvp.MVPView

/**
 * Create by Ngocji on 11/25/2018
 **/


interface RequestPermissionContract {
    interface View : MVPView {
        fun onGrantPermission()
        fun onDenyPermission(listDeny: List<String>)
        fun finishActivity()
        fun showTipDialog(permission: String, message: Pair<String, String>)
        fun showDialogLayout(message: Pair<String, String>)
        fun showDialogOption()
        fun onJustBlocked(list: List<String>)
        fun goToSetting()
        fun startSettings()
        fun startTutorial()
    }

    interface Presenter : MVPPresenter<View> {
        fun handleOnRestart()
        fun initRequestPermission(activity: Activity, permissions: Array<String>, hasValidLayoutRes: Boolean)
        fun handleRequestPermissionResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
        fun handleActivityResult(requestCode: Int)
        fun requestListDenyPermission(activity: Activity)
        fun deny()
        fun handleTipOkClicked(activity: Activity)
        fun handleOnStop()
    }
}