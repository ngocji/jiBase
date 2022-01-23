package com.jibase.permission

import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class PermissionsHelper<T> private constructor(val target: T) {
    companion object {
        // region set target
        @JvmStatic
        fun with(target: Fragment): PermissionsHelper<Fragment> {
            return PermissionsHelper(target)
        }

        @JvmStatic
        fun with(target: FragmentActivity): PermissionsHelper<FragmentActivity> {
            return PermissionsHelper(target)
        }
    }

    private var permissionFragment: PermissionFragment? = null

    init {
        getLazyPermissionFragment(target)
    }

    // region main
//    fun request(vararg permissions: String): Boolean {
//        invalidRequestPermission(permissions)
//        return requestImplementation(permissions.toList())
//                .buffer(permissions.size)
//                .flatMap { resultPermissions ->
//                    if (resultPermissions.isEmpty()) {
//                        // Occurs during orientation change, when the subject receives onComplete.
//                        // In that case we don't want to propagate that empty list to the
//                        // subscriber, only the onComplete.
//                        return@flatMap Observable.empty<Boolean>()
//                    }
//
//                    return@flatMap Observable.just(resultPermissions.all { it.granted })
//                }
//    }

    private fun invalidRequestPermission(permissions: Array<out String>) {
        if (permissions.isEmpty()) throw IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission")
    }

    /**
     * Returns true if the permission is already granted.
     *
     *
     * Always true if SDK &lt; 23.
     */
    fun isGranted(vararg permissions: String): Boolean {
        return !isRuntimeRequestPermission() || getPermissionFragment().isGranted(*permissions)
    }

    /**
     * Returns true if the permission has been revoked by a policy.
     *
     *
     * Always false if SDK &lt; 23.
     */
    fun isRevoked(vararg permissions: String): Boolean {
        return isRuntimeRequestPermission() && getPermissionFragment().isRevoked(*permissions)
    }

    fun isRuntimeRequestPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    // endregion

    // region init permission fragment
    private fun getLazyPermissionFragment(target: T) {
        val tag = PermissionFragment::class.java.name
        val fragmentManager = when (target) {
            is FragmentActivity -> target.supportFragmentManager
            is Fragment -> target.childFragmentManager
            else -> throw NullPointerException("Target must not be null")
        }
        permissionFragment = fragmentManager.findFragmentByTag(tag) as? PermissionFragment
                ?: // create newInstance
                        PermissionFragment().apply {
                            // add to manager
                            fragmentManager.beginTransaction()
                                    .add(this, tag)
                                    .commitNow()
                        }
    }
    // endregion

    // region private method
//    private fun requestImplementation(permissions: List<String>): Permission {
//        val list = mutableListOf<Observable<Permission>>()
//        val unrequestedPermissions = mutableListOf<String>()
//
//        // In case of multiple permissions, we create an Observable for each of them.
//        // At the end, the observables are combined to have a unique response.
//
//        // In case of multiple permissions, we create an Observable for each of them.
//        // At the end, the observables are combined to have a unique response.
//        permissions.forEach { permission ->
//            if (isGranted(permission)) {
//                list.add(Observable.just(Permission(permission, true, false)))
//                return@forEach
//            }
//
//            if (isRevoked(permission)) {
//                list.add(Observable.just(Permission(permission, false, false)))
//                return@forEach
//            }
//
//            val subject = getPermissionFragment().getSubjectByPermission(permission) {
//                unrequestedPermissions.add(permission)
//                PublishSubject.create()
//            }
//
//            list.add(subject)
//        }
//
//        if (unrequestedPermissions.isNotEmpty()) {
//            getPermissionFragment().requests(*unrequestedPermissions.toTypedArray())
//        }
//
//        return Observable.concat(Observable.fromIterable(list))
//    }

    private fun getPermissionFragment(): PermissionFragment {
        return permissionFragment
                ?: throw IllegalStateException("Permission is not implementation or not attach a context")
    }
    // endregion
}