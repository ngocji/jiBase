package com.jibase

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by ngoc on 6/7/2018.
 */
abstract class BaseApp : MultiDexApplication() {
    companion object {
        lateinit var instance: BaseApp
    }

    open var isEnableLog: Boolean = true

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance = this

        /**
         * config global error rxjava
         */
        RxJavaPlugins.setErrorHandler {}
    }
}
