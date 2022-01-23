package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.safeNavigate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_test_1.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_test_1)
class TestFragment : BaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {
       button.setOnClickListener {
           findNavController().safeNavigate(R.id.action_testFragment_to_test2Fragment)
       }
    }
}