package comx.y.z.kotlinbase.fragment.list

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.viewBinding
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_list)
class ListFragment : BaseFragment() {
    override fun onViewReady(savedInstanceState: Bundle?) {

    }
}