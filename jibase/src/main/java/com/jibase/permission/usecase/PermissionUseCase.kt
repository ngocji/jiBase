package com.jibase.permission.usecase

import com.jibase.permission.helper.Permissions
import com.jibase.permission.tippermisssion.*

/**
 * Create by Ngocji on 11/25/2018
 **/


interface PermissionUseCase {
    fun hasTipPermission(list:Array<String>):String?
    fun createTipPermission(permission: String): PermissionTip
}

class PermissionUseCaseImpl : PermissionUseCase {
    override fun hasTipPermission(list: Array<String>): String? {
      return  list.find {
            it == Permissions.PERMISSION_DRAW ||
                    it == Permissions.PERMISSION_ACCESSIBILITY ||
                    it == Permissions.PERMISSION_AUTO_START ||
                    it == Permissions.PERMISSION_USAGE_STATS
        }
    }

    override fun createTipPermission(permission: String): PermissionTip {
        return when (permission) {
            Permissions.PERMISSION_DRAW -> PermissionDraw()
            Permissions.PERMISSION_USAGE_STATS -> PermissionState()
            Permissions.PERMISSION_AUTO_START -> PermissionAutoStart()
            Permissions.PERMISSION_ACCESSIBILITY -> PermissionAccessibility()
            else -> PermissionDefault()
        }
    }
}