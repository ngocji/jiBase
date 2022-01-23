package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.findNavController
import com.jibase.extensions.safeNavigate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_test_2.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_test_2)
class Test2Fragment : BaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {

        button.setOnClickListener {
            findNavController(this).safeNavigate(R.id.action_testLogin1Fragment_to_testLogin2Fragment)
        }
    }
}