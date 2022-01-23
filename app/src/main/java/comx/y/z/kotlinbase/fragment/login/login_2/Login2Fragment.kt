package comx.y.z.kotlinbase.fragment.login.login_2

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.findNavController
import com.jibase.extensions.findParentNavController
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_test_login_2_2.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_test_login_2_2)
class Login2Fragment : BaseFragment() {
    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {

            findParentNavController(1)?.popBackStack(R.id.testLogin1Fragment, false)
        }
    }
}