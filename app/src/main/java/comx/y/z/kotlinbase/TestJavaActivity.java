package comx.y.z.kotlinbase;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jibase.anotation.Inflate;
import com.jibase.ui.base.BaseActivity;

import comx.y.z.kotlinbase.databinding.ActivityMainBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TestJavaActivity extends BaseActivity {
    @Inflate
    ActivityMainBinding binding;
    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

    }
}
