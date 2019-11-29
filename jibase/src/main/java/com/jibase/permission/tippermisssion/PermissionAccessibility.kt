package com.jibase.permission.tippermisssion

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import com.jibase.BaseApp
import com.jibase.R
import com.jibase.permission.helper.Permissions.PERMISSION_ACCESSIBILITY
import com.jibase.utils.getStringResource
import com.jibase.utils.log

class PermissionAccessibility : PermissionTip {
    override fun getPermission() = PERMISSION_ACCESSIBILITY

    override fun hasPermission(): Boolean {
        var accessibilityEnabled = 0
        val service = "${BaseApp.instance.packageName}/${BaseApp.instance.packageName}.service.AccessibilityService"
        val accessibilityFound = false
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    BaseApp.instance.applicationContext.contentResolver,
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
            log("Aceessibility Enable = $accessibilityEnabled")
        } catch (e: Settings.SettingNotFoundException) {
            log("Accessibility Error: $e")
        }

        val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

        if (accessibilityEnabled == 1) {
            log("Accessibility is Enable ----------------")
            val settingValue = Settings.Secure.getString(
                    BaseApp.instance.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessabilityService = mStringColonSplitter.next()

                    if (accessabilityService.equals(service, ignoreCase = true)) {
                        log("Accessibility ---: We've found the correct setting - accessibility is switched on")
                        return true
                    }
                }
            }
        } else {
            log("Accessibility Disable ------------")
        }

        return accessibilityFound
    }

    @Throws(Exception::class)
    override fun requestPermission(act: Activity, requestCode: Int) {
        act.startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), requestCode)
    }

    override fun getMessage() = Pair(getStringResource(R.string.per_title_accessibility), getStringResource(R.string.per_mess_accessibility))
}