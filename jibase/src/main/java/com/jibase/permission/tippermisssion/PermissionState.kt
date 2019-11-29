package com.jibase.permission.tippermisssion

import android.app.Activity
import android.app.AppOpsManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import com.jibase.BaseApp
import com.jibase.R
import com.jibase.permission.helper.Permissions.PERMISSION_AUTO_START
import com.jibase.utils.getStringResource

class PermissionState : PermissionTip {
    override fun getPermission() = PERMISSION_AUTO_START

    override fun hasPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return try {
                val packageManager = BaseApp.instance.packageManager
                val applicationInfo = packageManager.getApplicationInfo(BaseApp.instance.packageName, 0)
                val appOpsManager = BaseApp.instance.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                val mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName)
                mode == AppOpsManager.MODE_ALLOWED
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
        return true
    }

    @Throws(ActivityNotFoundException::class, Exception::class)
    override fun requestPermission(act: Activity, requestCode: Int) {
        act.startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), requestCode)
    }

    override fun getMessage() = Pair(getStringResource(R.string.per_title_usage), getStringResource(R.string.per_mess_usage))
}