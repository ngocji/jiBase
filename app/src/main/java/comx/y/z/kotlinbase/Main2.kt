package comx.y.z.kotlinbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jibase.helper.RxBusHelper

class Main2 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBusHelper.send("BALALALAA")
    }
}