package comx.y.z.kotlinbase;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.jibase.anotation.BindingInfo;
import com.jibase.ui.mvvm.BindActivity;

import org.jetbrains.annotations.Nullable;


@BindingInfo(layout = R.layout.activity_main, viewModel = Test2ViewModel.class)
public class TestActivity extends BindActivity<Test2ViewModel> {

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        getViewModel().test.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                TestDialog testDialog = new TestDialog();
                testDialog.show(getSupportFragmentManager(), null);
            }
        });

        getViewModel().test.post(true);
    }
}
