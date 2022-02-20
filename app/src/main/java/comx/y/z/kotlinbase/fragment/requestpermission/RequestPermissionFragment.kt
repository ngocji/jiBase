package comx.y.z.kotlinbase.fragment.requestpermission

import android.Manifest
import android.os.Bundle
import com.jibase.anotation.Inflate
import com.jibase.anotation.InflateActivityViewModel
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

    override fun onViewReady(savedInstanceState: Bundle?) {
        PermissionsHelper.with(this)
            .request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
            )
            .onGrant {
                Log.e("Granted")
            }
            .onDeny {
                Log.e("Deny: " + it.map { it.name }.joinToString(", "))
            }
            .onRevoke {
                Log.e("Revoke: " + it.map { it.name }.joinToString(", "))
            }
            .execute()

        Log.e("OnCount: ${mainViewModel.count}")
    }
}