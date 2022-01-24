package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.ViewInflate
import com.jibase.extensions.safeNavigate
import com.jibase.extensions.viewBinding
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_main)
class MainFragment : BaseFragment() {
    private val binding by viewBinding(FragmentMainBinding::bind)
    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.buttonRequestPermission.setOnClickListener {
            findNavController().safeNavigate(R.id.action_mainFragment_to_requestPermissionFragment)
        }
    }
}