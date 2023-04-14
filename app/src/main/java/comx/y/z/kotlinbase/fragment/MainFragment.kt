package comx.y.z.kotlinbase.fragment

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jibase.extensions.safeNavigate
import com.jibase.extensions.viewBinding
import com.jibase.permission.PermissionsHelper
import com.jibase.utils.Log
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRequestPermission.setOnClickListener {
            PermissionsHelper.with(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGrant {
                    Log.e("Grant")
                }
                .onDeny {
                    Log.e("Deny")
                }
                .execute()
        }
    }
}