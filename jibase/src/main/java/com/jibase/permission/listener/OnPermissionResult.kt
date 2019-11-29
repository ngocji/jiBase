package com.jibase.permission.listener

import com.jibase.utils.log


interface OnPermissionResult {
    fun onGranted()

    fun onDenied(deniedPermissions: List<String>) {
        log("OnPermission Denied: $deniedPermissions")
    }

    fun onBlock(blockPermissions: List<String>) {
        log("OnPermission Block: $blockPermissions")
    }

    fun onJustBlocked(justBlockPermissions: List<String>) {
        log("OnPermission Just Block: $justBlockPermissions")
        onDenied(justBlockPermissions)
    }
}