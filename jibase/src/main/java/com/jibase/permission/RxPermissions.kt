package com.jibase.permission

import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxPermissions {
    private var permissionFragment: RxPermissionFragment? = null

    // region set target
    @JvmStatic
    fun with(target: Fragment): RxPermissions {
        getLazyPermissionFragment(target.childFragmentManager)
        return this
    }

    @JvmStatic
    fun with(target: FragmentActivity): RxPermissions {
        getLazyPermissionFragment(target.supportFragmentManager)
        return this
    }

    // endregion

    // region main
    @JvmStatic
    fun request(vararg permissions: String): Observable<Boolean> {
        invalidRequestPermission(permissions)
        return requestImplementation(permissions.toList())
                .buffer(permissions.size)
                .flatMap { resultPermissions ->
                    if (resultPermissions.isEmpty()) {
                        // Occurs during orientation change, when the subject receives onComplete.
                        // In that case we don't want to propagate that empty list to the
                        // subscriber, only the onComplete.
                        return@flatMap Observable.empty<Boolean>()
                    }

                    return@flatMap Observable.just(resultPermissions.all { it.granted })
                }
    }

    @JvmStatic
    fun requestEach(vararg permissions: String): Observable<Permission> {
        invalidRequestPermission(permissions)
        return requestImplementation(permissions.toList())
    }

    @JvmStatic
    fun requestEachCombined(vararg permissions: String): Observable<Permission> {
        invalidRequestPermission(permissions)
        return requestImplementation(permissions.toList())
                .buffer(permissions.size)
                .flatMap { resultPermission ->
                    if (resultPermission.isEmpty()) {
                        Observable.empty()
                    } else {
                        Observable.just(Permission(resultPermission))
                    }
                }
    }

    private fun invalidRequestPermission(permissions: Array<out String>) {
        if (permissions.isEmpty()) throw IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission")
    }

    /**
     * Returns true if the permission is already granted.
     *
     *
     * Always true if SDK &lt; 23.
     */
    @JvmStatic
    fun isGranted(vararg permissions: String): Boolean {
        return !isRuntimeRequestPermission() || getPermissionFragment().isGranted(*permissions)
    }

    /**
     * Returns true if the permission has been revoked by a policy.
     *
     *
     * Always false if SDK &lt; 23.
     */
    @JvmStatic
    fun isRevoked(vararg permissions: String): Boolean {
        return isRuntimeRequestPermission() && getPermissionFragment().isRevoked(*permissions)
    }

    @JvmStatic
    fun isRuntimeRequestPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    // endregion

    // region init permission fragment
    private fun getLazyPermissionFragment(fragmentManager: FragmentManager) {
        val tag = RxPermissionFragment::class.java.name

        permissionFragment = fragmentManager.findFragmentByTag(tag) as? RxPermissionFragment
                ?: // create newInstance
                        RxPermissionFragment().apply {
                            // add to manager
                            fragmentManager.beginTransaction()
                                    .add(this, tag)
                                    .commitNow()
                        }
    }
    // endregion

    // region private method
    private fun requestImplementation(permissions: List<String>): Observable<Permission> {
        val list = mutableListOf<Observable<Permission>>()
        val unrequestedPermissions = mutableListOf<String>()

        // In case of multiple permissions, we create an Observable for each of them.
        // At the end, the observables are combined to have a unique response.

        // In case of multiple permissions, we create an Observable for each of them.
        // At the end, the observables are combined to have a unique response.
        permissions.forEach { permission ->
            if (isGranted(permission)) {
                list.add(Observable.just(Permission(permission, true, false)))
                return@forEach
            }

            if (isRevoked(permission)) {
                list.add(Observable.just(Permission(permission, false, false)))
                return@forEach
            }

            val subject = getPermissionFragment().getSubjectByPermission(permission) {
                unrequestedPermissions.add(permission)
                PublishSubject.create()
            }

            list.add(subject)
        }

        if (unrequestedPermissions.isNotEmpty()) {
            getPermissionFragment().requests(*unrequestedPermissions.toTypedArray())
        }

        return Observable.concat(Observable.fromIterable(list))
    }

    private fun getPermissionFragment(): RxPermissionFragment {
        return permissionFragment
                ?: throw IllegalStateException("Permission is not implementation or not attach a context")
    }
    // endregion
}