package comx.y.z.kotlinbase;

import android.os.Bundle;

import com.jibase.anotation.ViewInflate;
import com.jibase.ui.base.BaseActivity;
import com.jibase.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@ViewInflate(layout = R.layout.activity_main, viewModel = Test2ViewModel.class)
public class TestActivity extends BaseActivity<Test2ViewModel> {
    @NotNull
    @Override
    public Test2ViewModel getViewModel() {
        return super.getViewModel();
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
        TestDialog.Companion.newInstance(new TestDialog.Callback() {
            @Override
            public void onClicked() {
                ToastUtils.showText(TestActivity.this, "Clciekd");
            }
        }).show(getSupportFragmentManager());
    }
}
