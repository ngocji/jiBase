package comx.y.z.kotlinbase;

import android.app.Application;

import com.jibase.utils.Log;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.setup("A", true);
        Log.e("Error");
    }
}
