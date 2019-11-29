package com.jibase.permission.helper

import android.content.pm.PackageManager
import android.os.Build
import com.jibase.BaseApp
import com.jibase.permission.RequestPermissionActivity
import com.jibase.permission.entities.DialogOption
import com.jibase.permission.listener.OnPermissionResult
import com.jibase.permission.usecase.PermissionUseCase
import com.jibase.permission.usecase.PermissionUseCaseImpl

object Permissions : PermissionRequest {
    const val PERMISSION_DRAW = "permission.draw_screen"
    const val PERMISSION_AUTO_START = "permission.auto_start"
    const val PERMISSION_USAGE_STATS = "permission.usage_stats"
    const val PERMISSION_ACCESSIBILITY = "permission.accessibility"
    private val permissionUseCase: PermissionUseCase = PermissionUseCaseImpl()

    override fun has(permissions: Array<String>): Boolean {
        val tipPermission = permissionUseCase.hasTipPermission(permissions)
        if (tipPermission != null) {
            return permissionUseCase.createTipPermission(tipPermission).hasPermission()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (per in permissions) {
                    if (BaseApp.instance.checkCallingOrSelfPermission(per) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
                return true
            } else {
                return true
            }
        }
    }

    override fun request(permissions: Array<String>,
                         callback: OnPermissionResult,
                         layoutRes: Int,
                         option: DialogOption?,
                         activityTutorial: Class<*>?) {
        if (has(permissions)) {
            callback.onGranted()
        } else {
            RequestPermissionActivity.start(permissions, callback, layoutRes, option, activityTutorial)
        }
    }
}