package comx.y.z.kotlinbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.entities.livedata.ILiveData
import com.jibase.entities.livedata.ISingleLiveData
import com.jibase.extensions.observe
import com.jibase.utils.log

class MainActivity : AppCompatActivity() {
    val single = ISingleLiveData<Boolean>(false)
    val live = ILiveData<Boolean>(isNotifyData = true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // todo log
        single.observe(this) {
            log("Single Change--> " + it)
        }

        live.observe(this) {
            log("Live Change --> " + it)
        }

        live.post(true)
    }
}
