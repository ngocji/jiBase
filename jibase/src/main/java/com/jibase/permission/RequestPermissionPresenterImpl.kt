package com.jibase.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.jibase.R
import com.jibase.extensions.addNeedClear
import com.jibase.helper.pref.SharePreferencesHelper.put
import com.jibase.permission.RequestPermissionActivity.Companion.RC_PERMISSION
import com.jibase.permission.RequestPermissionActivity.Companion.RC_SETTINGS
import com.jibase.permission.RequestPermissionActivity.Companion.RC_TIP_PERMISSION
import com.jibase.permission.helper.Permissions
import com.jibase.permission.helper.Permissions.PERMISSION_AUTO_START
import com.jibase.permission.helper.Permissions.PERMISSION_DRAW
import com.jibase.permission.tippermisssion.PermissionTip
import com.jibase.permission.usecase.PermissionUseCase
import com.jibase.permission.usecase.PermissionUseCaseImpl
import com.jibase.ui.mvp.MVPPresenterImpl
import com.jibase.utils.getStringResource

/**
 * Create by Ngocji on 11/25/2018
 **/

@RequiresApi(Build.VERSION_CODES.M)
class RequestPermissionPresenterImpl(mView: RequestPermissionContract.View) : MVPPresenterImpl<RequestPermissionContract.View>(mView), RequestPermissionContract.Presenter {
    private val listDeny = mutableListOf<String>()
    private val listAll = mutableListOf<String>()
    private val listRational = mutableListOf<String>()

    private val permissionUseCase: PermissionUseCase = PermissionUseCaseImpl()
    private var permissionTip: PermissionTip? = null

    private var isGoToSetting = false


    override fun initRequestPermission(activity: Activity, permissions: Array<String>, hasValidLayoutRes: Boolean) {
        if (permissions.isEmpty()) {
            grant()
        } else {
            listAll.addNeedClear(permissions)
            permissionUseCase.hasTipPermission(permissions)?.also { per ->
                permissionTip = permissionUseCase.createTipPermission(per).apply {
                    mView.showTipDialog(per, this.getMessage())
                }
                return
            }

            //request normal permission
            var isShouldShowRequest = true
            listAll.forEach {
                if (activity.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                    listDeny.add(it)
                    if (activity.shouldShowRequestPermissionRationale(it)) {
                        isShouldShowRequest = false
                    } else {
                        listRational.add(it)
                    }
                }
            }

            if (listDeny.isEmpty()) {
                grant()
                return
            }

            if (isShouldShowRequest) {
                activity.requestPermissions(listDeny.toTypedArray(), RC_PERMISSION)
            } else {
                if (hasValidLayoutRes) {
                    mView.showDialogLayout(Pair(getStringResource(R.string.per_title_default), getStringResource(R.string.per_mess_default)))
                } else {
                    mView.showDialogOption()
                }
            }
        }
    }

    override fun handleOnRestart() {
        permissionTip?.also {
            when (it.getPermission()) {
                PERMISSION_AUTO_START -> {
                    put(it.getPermission(), true)
                    grant()
                }
                PERMISSION_DRAW -> {
                    if (it.hasPermission()) {
                        grant()
                    } else {
                        deny()
                    }
                }
            }
        }
    }

    override fun handleOnStop() {
        if (isGoToSetting) {
            mView.startTutorial()
        }
    }

    override fun handleRequestPermissionResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) {
            deny()
        } else {
            listDeny.clear()
            grantResults.forEachIndexed { index, i ->
                if (i != PackageManager.PERMISSION_GRANTED) {
                    listDeny.add(permissions[index])
                }
            }
            if (listDeny.isEmpty()) {
                grant()
            } else {
                val blockedList = mutableListOf<String>() //set not to ask again.
                val justBlockedList = mutableListOf<String>() //just set not to ask again.
                val justDeniedList = mutableListOf<String>()
                listDeny.forEach {
                    if (activity.shouldShowRequestPermissionRationale(it)) {
                        justDeniedList.add(it)
                    } else {
                        blockedList.add(it)
                        if (!listRational.contains(it)) {
                            justBlockedList.add(it)
                        }
                    }
                }
                when {
                    justBlockedList.isNotEmpty() -> {
                        justBlocked(justBlockedList)
                    }
                    justDeniedList.isNotEmpty() -> deny()
                    justBlockedList.isNotEmpty() -> mView.goToSetting()
                    else -> deny()
                }
            }

        }
    }

    override fun handleActivityResult(requestCode: Int) {
        when (requestCode) {
            RC_SETTINGS -> {
                if (Permissions.has(listAll.toTypedArray())) {
                    grant()
                } else {
                    deny()
                }
            }
            else -> {
                permissionTip?.also {
                    if (it.hasPermission()) {
                        grant()
                    } else {
                        deny()
                    }
                }
            }
        }
    }

    override fun handleTipOkClicked(activity: Activity) {
        if (permissionTip != null) {
            permissionTip?.also {
                it.requestPermission(activity, RC_TIP_PERMISSION)
            }
        } else {
            isGoToSetting = true
            mView.startSettings()
        }
    }

    override fun requestListDenyPermission(activity: Activity) {
        activity.requestPermissions(listDeny.toTypedArray(), RC_PERMISSION)
    }

    private fun justBlocked(list: List<String>) {
        mView.onJustBlocked(list)
        mView.finishActivity()
    }

    private fun grant() {
        mView.onGrantPermission()
        mView.finishActivity()
    }


    override fun deny() {
        mView.onDenyPermission(listDeny)
        mView.finishActivity()
    }

    override fun detach() {
    }
}