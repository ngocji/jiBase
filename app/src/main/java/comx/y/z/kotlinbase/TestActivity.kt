package comx.y.z.kotlinbase

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.ui.BaseViewModel
import com.jibase.ui.base.BaseActivity

@ViewInflate(layout = R.layout.activity_main, viewModel = BaseViewModel::class)
class TestActivity : BaseActivity<BaseViewModel>() {

    override fun onViewReady(savedInstanceState: Bundle?) {
        findNav
    }
}