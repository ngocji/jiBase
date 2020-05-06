package com.jibase

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.jibase.di.baseModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            // declare used Android context
            androidContext(this@BaseApp)
            // declare modules
            modules(initModules())
        }

        /**
         * config global error rx java
         */
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initModules(): List<Module> {
        val modules = mutableListOf<Module>()
        modules.add(baseModule)
        initAppModule(modules)
        return modules
    }

    open fun initAppModule(modules: MutableList<Module>) {
        // free add more module
    }
}
