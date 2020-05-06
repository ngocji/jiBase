package comx.y.z.kotlinbase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.helper.event.registerEvent
import com.jibase.utils.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerEvent<String> {
            Log.e("OnEvent receiver: $it")
        }


        startActivity(Intent(this, Main2::class.java))
    }
}
