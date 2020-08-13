package comx.y.z.kotlinbase;

import android.Manifest;
import android.os.Bundle;

import com.jibase.anotation.Inflate;
import com.jibase.permission.RxPermissions;
import com.jibase.ui.binding.BindActivity;
import com.jibase.utils.Log;

import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Consumer;


@Inflate(layout = R.layout.activity_main, viewModel = Test2ViewModel.class)
public class TestActivity extends BindActivity<Test2ViewModel> {

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        RxPermissions.with(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e("On grant: " + aBoolean);
                    }
                });
    }
}
