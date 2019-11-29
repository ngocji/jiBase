package com.jibase.permission.tippermisssion

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import com.jibase.R
import com.jibase.permission.helper.Permissions.PERMISSION_AUTO_START
import com.jibase.utils.getStringResource

class PermissionAutoStart : PermissionTip {
    override fun getPermission() = PERMISSION_AUTO_START

    override fun hasPermission(): Boolean {
        val manufacturer = android.os.Build.MANUFACTURER
        return when {
            "xiaomi".equals(manufacturer, ignoreCase = true) -> true
            "oppo".equals(manufacturer, ignoreCase = true) -> true
            "vivo".equals(manufacturer, ignoreCase = true) -> true
            "Letv".equals(manufacturer, ignoreCase = true) -> true
            "Honor".equals(manufacturer, ignoreCase = true) -> true
            else -> false
        }
    }

    @Throws(Exception::class)
    override fun requestPermission(act: Activity, requestCode: Int) {
        val intent = Intent()
        val manufacturer = android.os.Build.MANUFACTURER
        when {
            "xiaomi".equals(manufacturer, ignoreCase = true) -> intent.component = ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
            "oppo".equals(manufacturer, ignoreCase = true) -> intent.component = ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")
            "vivo".equals(manufacturer, ignoreCase = true) -> intent.component = ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")
            "Letv".equals(manufacturer, ignoreCase = true) -> intent.component = ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")
            "Honor".equals(manufacturer, ignoreCase = true) -> intent.component = ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")
        }
        val list = act.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.size > 0) {
            act.startActivity(intent)
        }
    }

    override fun getMessage() = Pair(getStringResource(R.string.per_title_autostart), getStringResource(R.string.per_mess_autostart))

}