package comx.y.z.kotlinbase.fragment

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jibase.extensions.viewBinding
import com.jibase.helper.MediaStoreHelper
import com.jibase.permission.PermissionsHelper
import com.jibase.utils.Log
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentMainBinding
import comx.y.z.kotlinbase.fragment.dialog.TestDialog
import java.io.File

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRequestPermission.setOnClickListener {
            TestDialog().show(childFragmentManager)
        }
    }
}