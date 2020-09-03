package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.ui.base.SimpleBaseFragment
import com.jibase.utils.ToastUtils
import comx.y.z.kotlinbase.R

@ViewInflate(layout = R.layout.fragment_test_2, enableBackPressed = true)
class Test2Fragment : SimpleBaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {

    }

    override fun onBackPressed() {
        ToastUtils.showText(requireContext(), "onBack test 2")
        findNavController().popBackStack()
    }
}