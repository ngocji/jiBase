package comx.y.z.kotlinbase;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jibase.helper.ViewModelHelper;
import com.jibase.pref.SharePref;

import javax.inject.Inject;

import comx.y.z.kotlinbase.fragment.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TestJavaActivity extends AppCompatActivity {

    TestJavaViewModel testJavaViewModel;

    public TestJavaActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testJavaViewModel = ViewModelHelper.newViewModel(this, TestJavaViewModel.class);

        testJavaViewModel.count();
    }

//    @Override
//    public void onViewReady(@Nullable Bundle savedInstanceState) {
//        setContentView(R.layout.activity_main);
//        pref.put("x", 1);
////        mainViewModel = ViewModelHelper.newViewModel(this, MainViewModel.class);
////
////        mainViewModel.setCount(1);
//
//    }
}
