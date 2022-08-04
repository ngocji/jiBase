package comx.y.z.kotlinbase

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jibase.pref.SharePref
import com.jibase.utils.Log
import comx.y.z.kotlinbase.fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var sharePref: SharePref
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharePref.put("xcz", 100)

        val x = sharePref.getLong("abc", 0L)

    }
}