package comx.y.z.kotlinbase.fragment.login.login_2

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_test_1.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_test_login_1_2)
class Login1Fragment : BaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_login1Fragment_to_login2Fragment)
        }
    }
}