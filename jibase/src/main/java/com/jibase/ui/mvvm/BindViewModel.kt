package com.jibase.ui.mvvm

import androidx.lifecycle.ViewModel
import org.koin.core.Koin
import org.koin.core.KoinComponent

abstract class BindViewModel : ViewModel(), KoinComponent{
    override fun getKoin(): Koin {
        return super.getKoin()
    }
}