package com.jibase

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import io.reactivex.plugins.RxJavaPlugins

abstract class BaseApp : MultiDexApplication() {
    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        BaseApp.applicationContext = this.applicationContext

        MultiDex.install(this)

        /**
         * config global error rx java
         */
        RxJavaPlugins.setErrorHandler {}
    }
}
