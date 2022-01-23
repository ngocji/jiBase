package comx.y.z.kotlinbase.fragment.login

import android.os.Bundle
import android.view.View
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.findNavController
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_test_login_2.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_test_login_2)
class TestLogin2Fragment : BaseFragment() {
    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {

            requireActivity().findViewById<View>(R.id.nav_host_fragment)
                .findNavController().popBackStack(R.id.testFragment, false)

        }
    }
}