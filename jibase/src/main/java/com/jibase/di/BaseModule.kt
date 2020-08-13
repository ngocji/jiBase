package com.jibase.di

import com.jibase.helper.GsonHelper
import com.jibase.helper.KeyboardHelper
import com.jibase.ui.BindViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// this file impl base module
val baseModule = module {
    single { GsonHelper() }
    single { KeyboardHelper(get()) }
    viewModel { BindViewModel() }
}