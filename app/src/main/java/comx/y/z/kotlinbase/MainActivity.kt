package comx.y.z.kotlinbase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import comx.y.z.jicore.BaseAct

class MainActivity : BaseAct() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
