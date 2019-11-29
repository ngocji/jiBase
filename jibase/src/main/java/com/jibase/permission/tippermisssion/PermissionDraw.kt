package com.jibase.permission.tippermisssion

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.provider.Settings
import com.jibase.BaseApp
import com.jibase.R
import com.jibase.permission.helper.Permissions.PERMISSION_DRAW
import com.jibase.utils.getStringResource
import java.io.File
import java.io.FileInputStream
import java.util.*

class PermissionDraw : PermissionTip {
    override fun getPermission() = PERMISSION_DRAW

    override fun hasPermission(): Boolean {
        if (isMi()) {
            val manager = BaseApp.instance.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val localClass = manager.javaClass
            val arrayOfClass = arrayOfNulls<Class<*>>(3)
            arrayOfClass[0] = Integer.TYPE
            arrayOfClass[1] = Integer.TYPE
            arrayOfClass[2] = String::class.java
            try {
                val method = localClass.getMethod("checkOp", *arrayOfClass) ?: return false
                val arrayOfObjects = arrayOfNulls<Any>(3)
                arrayOfObjects[0] = Integer.valueOf(24)
                arrayOfObjects[1] = Integer.valueOf(Binder.getCallingUid())
                arrayOfObjects[2] = BaseApp.instance.getPackageName()
                val m = (method.invoke(manager as Any, *arrayOfObjects) as Int).toInt()
                return m == AppOpsManager.MODE_ALLOWED
            } catch (e: Exception) {
                return false
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(BaseApp.instance)) return false
            }
            return true
        }
    }

    override fun requestPermission(act: Activity, requestCode: Int) {
        if (isMi()) {
            try {
                // MIUI 8
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
                localIntent.putExtra("extra_pkgname", BaseApp.instance.getPackageName())
                act.startActivityForResult(localIntent, requestCode)
            } catch (e: Exception) {
                try {
                    // MIUI 5/6/7
                    val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                    localIntent.putExtra("extra_pkgname", BaseApp.instance.getPackageName())
                    act.startActivityForResult(localIntent, requestCode)
                } catch (e1: Exception) {
                    // Otherwise jump to application details
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", BaseApp.instance.getPackageName(), null)
                    intent.data = uri
                    try {
                        act.startActivityForResult(intent, requestCode)
                    } catch (e: Exception) {
                    }
                }

            }
        } else {
            try {
                act.startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION), requestCode)
            } catch (e: Exception) {
            }
        }
    }

    override fun getMessage() = Pair(getStringResource(R.string.per_title_draw), getStringResource(R.string.per_mess_draw))

    private fun isMi(): Boolean {
        val device = Build.MANUFACTURER
        if (device == "Xiaomi") {
            try {
                val prop = Properties()
                prop.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
                return (prop.getProperty("ro.miui.ui.version.code", null) != null
                        || prop.getProperty("ro.miui.ui.version.name", null) != null
                        || prop.getProperty("ro.miui.internal.storage", null) != null)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return false

    }
}