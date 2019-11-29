package com.jibase.permission.tippermisssion

import android.app.Activity

/**
 * Create by Ngocji on 11/24/2018
 **/


interface PermissionTip {
    fun getPermission(): String
    fun hasPermission(): Boolean
    fun requestPermission(act: Activity, requestCode: Int)
    fun getMessage(): Pair<String, String>
}