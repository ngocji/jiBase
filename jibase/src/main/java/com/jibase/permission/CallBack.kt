package com.jibase.permission

interface OnDenyPermissionListener{
    fun onDeny(permissions:List<Permission>)
}

interface OnRevokePermissionListener {
    fun onRevoke(permissions: List<Permission>)
}