package com.jibase.permission.tippermisssion

import android.app.Activity
import com.jibase.R
import com.jibase.utils.getStringResource

/**
 * Create by Ngocji on 11/24/2018
 **/


class PermissionDefault : PermissionTip {
    override fun getPermission()= ""

    override fun hasPermission() = true

    override fun requestPermission(act: Activity, requestCode: Int) {
        //not request permission
    }
    override fun getMessage() = Pair(getStringResource(R.string.per_title_default), getStringResource(R.string.per_mess_default))
}