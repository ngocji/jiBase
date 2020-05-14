package comx.y.z.kotlinbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.utils.FragmentUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentUtils.replace(
                this,
                R.id.replace,
                true,
                Fragment1())
    }
}
