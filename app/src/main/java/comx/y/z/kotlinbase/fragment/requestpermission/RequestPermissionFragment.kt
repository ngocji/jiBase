package comx.y.z.kotlinbase.fragment.requestpermission

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jibase.extensions.onBackPressedOverride
import com.jibase.extensions.viewBinding
import com.jibase.permission.OnDenyPermissionListener
import com.jibase.permission.Permission
import com.jibase.permission.PermissionsHelper
import com.jibase.pref.SharePref
import com.jibase.ui.base.BaseFragment
import com.jibase.utils.Log
import comx.y.z.kotlinbase.R
import comx.y.z.kotlinbase.databinding.FragmentRequestPermissionBinding
import comx.y.z.kotlinbase.fragment.MainViewModel
import javax.inject.Inject

class RequestPermissionFragment : BaseFragment(R.layout.fragment_request_permission) {
    private val mainViewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(FragmentRequestPermissionBinding::bind)

    var isBack = false

    @Inject
    lateinit var sharePref: SharePref

    override fun onViewReady(savedInstanceState: Bundle?) {
        sharePref.put("x", 1)
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