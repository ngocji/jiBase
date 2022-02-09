package comx.y.z.kotlinbase

import android.os.Bundle
import com.jibase.anotation.InflateViewModel
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.BaseActivity
import comx.y.z.kotlinbase.fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ViewInflate(layout = R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun onViewReady(savedInstanceState: Bundle?) {

    }
}