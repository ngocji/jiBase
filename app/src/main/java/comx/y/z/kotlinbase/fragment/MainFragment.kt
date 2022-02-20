package comx.y.z.kotlinbase.fragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateActivityViewModel
import com.jibase.extensions.safeNavigate
import com.jibase.ui.base.BaseFragment
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    @InflateActivityViewModel
    private lateinit var mainViewModel: MainViewModel

    @Inflate
    private lateinit var binding: FragmentMainBinding

    override fun onViewReady(savedInstanceState: Bundle?) {
        mainViewModel.count += 1

        binding.buttonRequestPermission.setOnClickListener {
            findNavController().safeNavigate(R.id.action_mainFragment_to_requestPermissionFragment)
        }
    }
}