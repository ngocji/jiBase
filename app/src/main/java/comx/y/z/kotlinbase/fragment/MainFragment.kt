package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.safeNavigate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_main)
class MainFragment : BaseFragment() {
    override fun onViewReady(savedInstanceState: Bundle?) {
        buttonRequestPermission.setOnClickListener {
            findNavController().safeNavigate(R.id.action_mainFragment_to_requestPermissionFragment)
        }
    }
}