package comx.y.z.kotlinbase.fragment.login

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_test_1.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_test_login_1)
class TestLogin1Fragment : BaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_testLogin1Fragment_to_testLogin2Fragment)
        }
    }
}