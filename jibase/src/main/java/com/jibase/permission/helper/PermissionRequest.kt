package com.jibase.permission.helper

import androidx.annotation.LayoutRes
import com.jibase.permission.entities.DialogOption
import com.jibase.permission.listener.OnPermissionResult

/**
 * Create by Ngocji on 11/24/2018
 **/


interface PermissionRequest {
    fun has(permissions: Array<String>):Boolean

    fun request(permissions: Array<String>,
                callback: OnPermissionResult,
                @LayoutRes layoutRes: Int = -1,
                option: DialogOption? = null,
                activityTutorial: Class<*>? = null)
}