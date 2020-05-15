@file:JvmName("Di")

package comx.y.z.kotlinbase

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val vm = module {
    viewModel { Test2ViewModel() }
}