package com.jibase.permission

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.BaseFragment

@ViewInflate
class PermissionFragment : BaseFragment() {
    companion object {
        const val REQ_PERMISSION = 1
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
    }

    override fun onViewListener() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQ_PERMISSION) return
        doPermissionResult(permissions, grantResults)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requests(vararg permissions: String) {
        requestPermissions(permissions, REQ_PERMISSION)
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
            fragmentActivity.packageManager.isPermissionRevokedByPolicy(it, fragmentActivity.packageName)
        }
    }

    private fun doPermissionResult(permissions: Array<out String>, grantResults: IntArray) {
//        permissions.forEachIndexed { index, permission ->
//            subjects[permission]?.run {
//                val granted = grantResults[index] == PackageManager.PERMISSION_GRANTED
//                val shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(permission)
//                onNext(Permission(permission, granted, shouldShowRequestPermissionRationale))
//                onComplete()
//            }
//
//            subjects.remove(permission)
//        }
    }
}