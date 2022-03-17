package comx.y.z.kotlinbase.fragment

import androidx.lifecycle.ViewModel
import com.jibase.pref.SharePref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharePref: SharePref
) : ViewModel() {
    var count = 1
}