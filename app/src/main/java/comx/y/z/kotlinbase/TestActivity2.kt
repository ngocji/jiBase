package comx.y.z.kotlinbase

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.BaseActivity

@ViewInflate(layout = R.layout.activity_main, viewModel = Test2ViewModel::class)
class TestActivity2 : BaseActivity<Test2ViewModel>() {

    override fun onViewReady(savedInstanceState: Bundle?) {
    }
}