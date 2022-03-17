package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jibase.extensions.safeNavigate
import com.jibase.extensions.viewBinding
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRequestPermission.setOnClickListener {
            findNavController().safeNavigate(R.id.action_mainFragment_to_requestPermissionFragment)
        }
    }
}