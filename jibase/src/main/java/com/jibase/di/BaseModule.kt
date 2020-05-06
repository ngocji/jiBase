package com.jibase.di

import com.jibase.helper.gson.GsonHelper
import com.jibase.helper.keyboard.KeyboardHelper
import org.koin.dsl.module

// this file impl base module
val baseModule = module {
    single { GsonHelper() }
    single { KeyboardHelper(get()) }
}