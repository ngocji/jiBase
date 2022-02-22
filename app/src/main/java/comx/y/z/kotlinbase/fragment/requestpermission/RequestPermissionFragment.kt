package comx.y.z.kotlinbase.fragment.requestpermission

import android.Manifest
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateActivityViewModel
import com.jibase.extensions.onBackPressedOverride
import com.jibase.permission.OnDenyPermissionListener
import com.jibase.permission.Permission
import com.jibase.permission.PermissionsHelper
import com.jibase.ui.base.BaseFragment
import com.jibase.utils.Log
import comx.y.z.kotlinbase.databinding.FragmentRequestPermissionBinding
import comx.y.z.kotlinbase.fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestPermissionFragment : BaseFragment() {
    @InflateActivityViewModel
    lateinit var mainViewModel: MainViewModel

    @Inflate
    lateinit var binding: FragmentRequestPermissionBinding

    var isBack = false

    override fun onViewReady(savedInstanceState: Bundle?) {
        PermissionsHelper.with(this)
            .request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
            )
            .onDeny(object : OnDenyPermissionListener {
                override fun onDeny(permissions: List<Permission>) {
                    Log.e("Run deny")
                }
            })
            .execute()

        onBackPressedOverride {
            if (isBack) {
                findNavController().popBackStack()
            } else {
                isBack = true
            }
        }
    }
}