package comx.y.z.kotlinbase.fragment.login

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.ui.base.SimpleBaseFragment
import com.jibase.utils.ToastUtils
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.TestHolder
import kotlinx.android.synthetic.main.fragment_test_1.*
import kotlinx.android.synthetic.main.fragment_test_1.button
import kotlinx.android.synthetic.main.fragment_test_login_1.*

@ViewInflate(layout = R.layout.fragment_test_login_1, enableBackPressed = false)
class TestLogin1Fragment : SimpleBaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_testLogin1Fragment_to_testLogin2Fragment)
        }
    }

    override fun onBackPressed() {
        ToastUtils.showText(requireContext(), "OnBack login 1")
        findNavController().popBackStack(R.id.action_testFragment_to_test2Fragment, false)
    }
}