package com.jibase.utils

import android.os.Build
import com.jibase.BaseApp

fun getVersionCode(): Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    BaseApp.instance.packageManager
            .getPackageInfo(BaseApp.instance.packageName, 0).longVersionCode
} else {
    BaseApp.instance.packageManager
            .getPackageInfo(BaseApp.instance.packageName, 0).versionCode.toLong()
}

fun getVersionName() = BaseApp.instance.packageManager
        .getPackageInfo(BaseApp.instance.packageName, 0).versionName