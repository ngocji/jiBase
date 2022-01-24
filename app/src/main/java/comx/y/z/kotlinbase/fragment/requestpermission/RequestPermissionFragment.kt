package comx.y.z.kotlinbase.fragment.requestpermission

import android.Manifest
import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.permission.PermissionsHelper
import com.jibase.ui.base.BaseFragment
import com.jibase.utils.Log
import comx.y.z.kotlinbase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ViewInflate(layout = R.layout.fragment_request_permission)
class RequestPermissionFragment : BaseFragment() {
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
    }
}