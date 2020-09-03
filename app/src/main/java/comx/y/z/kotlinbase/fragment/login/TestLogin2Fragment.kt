package comx.y.z.kotlinbase.fragment.login

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.SimpleBaseFragment
import com.jibase.utils.ToastUtils
import com.jibase.view.ISnackBar
import comx.y.z.kotlinbase.R
import kotlinx.android.synthetic.main.fragment_test_login_2.*

@ViewInflate(layout = R.layout.fragment_test_login_2, enableBackPressed = true)
class TestLogin2Fragment : SimpleBaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {
            ISnackBar()
                    .of(replace)
                    .withMessage("Snack bar view")
                    .withActionName("LALALA")
                    .create()
                    .show()

        }
    }

    override fun onBackPressed() {
        ToastUtils.showText(requireContext(), "OnBack login 2")
        requireActivity().findNavController(R.id.nav_login).popBackStack()
    }
}