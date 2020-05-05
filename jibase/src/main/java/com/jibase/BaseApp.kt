package com.jibase

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

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
        instance = this
        MultiDex.install(this)

        startKoin {
            // declare used Android context
            androidContext(this@BaseApp)
            // declare modules
            modules(initModules())
        }

        /**
         * config global error rxjava
         */
        RxJavaPlugins.setErrorHandler {}
    }

    abstract fun initModules(): List<Module>
}
