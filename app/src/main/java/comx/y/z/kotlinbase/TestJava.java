package comx.y.z.kotlinbase;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jibase.utils.Log;
import com.jibase.utils.SharePreferencesUtils;

public class TestJava extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharePreferencesUtils.putPref("data", new Test("TTTTTTTTTTTT"));

        Test data = SharePreferencesUtils.getPref("data", new Test("Default"), Test.class);
        Log.e("data: " + data.data);
    }


    class Test {
        public String data;

        public Test(String data) {
            this.data = data;
        }
    }
}
