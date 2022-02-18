package comx.y.z.kotlinbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jibase.anotation.InflateViewModel;
import com.jibase.anotation.ViewInflate;
import com.jibase.ui.base.BaseActivity;
import com.jibase.utils.Log;

import comx.y.z.kotlinbase.fragment.MainViewModel;

@ViewInflate(layout = R.layout.activity_main)
public class TestJavaActivity extends BaseActivity {
    @InflateViewModel
    MainViewModel mainViewModel;

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        mainViewModel.setCount(2);

        Log.e("OnView: ", mainViewModel.getCount() + "");
    }
}
