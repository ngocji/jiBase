package comx.y.z.kotlinbase;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jibase.helper.ViewModelHelper;
import com.jibase.ui.base.BaseActivity;

import comx.y.z.kotlinbase.databinding.ActivityMainBinding;
import comx.y.z.kotlinbase.fragment.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;

public class TestJavaActivity extends BaseActivity {

    MainViewModel mainViewModel;

    public TestJavaActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        mainViewModel = ViewModelHelper.INSTANCE.newViewModel(this, MainViewModel.class);
        mainViewModel.getCount();
    }
}
