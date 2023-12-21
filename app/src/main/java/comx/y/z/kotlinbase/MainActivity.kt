package comx.y.z.kotlinbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.utils.FragmentUtils
import com.jibase.utils.Log
import comx.y.z.kotlinbase.fragment.MainFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FragmentUtils.add(
            FragmentUtils.ReplaceOption<MainActivity>()
                .with(this)
                .setContainerId(R.id.flReplace)
                .setFragment(MainFragment())
                .addToBackStack(true)
        )

        FragmentUtils.handleBackPress(this) {stackCount->
            if (stackCount == 0) {

                true
            }else {
                false
            }
        }
    }
}