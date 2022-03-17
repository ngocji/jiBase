package comx.y.z.kotlinbase

import android.os.Bundle
import androidx.activity.viewModels
import com.jibase.ui.base.BaseActivity
import comx.y.z.kotlinbase.fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : BaseActivity(R.layout.activity_main) {
val viewModel by viewModels<MainViewModel>()
    override fun onViewReady(savedInstanceState: Bundle?) {

    }
}