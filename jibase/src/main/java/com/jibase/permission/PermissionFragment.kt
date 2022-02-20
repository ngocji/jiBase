package com.jibase.permission

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.jibase.ui.base.BaseFragment

class PermissionFragment : BaseFragment() {
    companion object {
        const val REQ_PERMISSION = 1
    }

    private var resultAction: ((Array<out String>, IntArray) -> Unit)? = null

    override fun onViewReady(savedInstanceState: Bundle?) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQ_PERMISSION) return
        resultAction?.invoke(permissions, grantResults)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requests(permissions: List<String>, action: (Array<out String>, IntArray) -> Unit) {
        resultAction = action
        requestPermissions(permissions.toTypedArray(), REQ_PERMISSION)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isGranted(vararg permissions: String): Boolean {
        val fragmentActivity = activity
            ?: throw IllegalStateException("This fragment must be attached to an activity.")
        return permissions.all {
            fragmentActivity.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isRevoked(vararg permissions: String): Boolean {
        val fragmentActivity = activity
            ?: throw IllegalStateException("This fragment must be attached to an activity.")
        return permissions.all {
            fragmentActivity.packageManager.isPermissionRevokedByPolicy(
                it,
                fragmentActivity.packageName
            )
        }
    }
}