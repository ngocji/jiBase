package comx.y.z.kotlinbase.fragment.list

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_list)
class ListFragment : BaseFragment() {
    override fun onViewReady(savedInstanceState: Bundle?) {
    }
}