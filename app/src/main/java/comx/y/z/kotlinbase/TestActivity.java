package comx.y.z.kotlinbase;

import android.content.Intent;
import android.os.Bundle;

import com.jibase.anotation.Inflate;
import com.jibase.ui.mvvm.BindActivity;

import org.jetbrains.annotations.Nullable;


@Inflate(layout = R.layout.activity_main, viewModel = Test2ViewModel.class)
public class TestActivity extends BindActivity<Test2ViewModel> {

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        try {
            if (!GoogleFitHelper.enableIfNeed(this, 1)){
                performActionForRequestCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            performActionForRequestCode();
        }
    }

    private void performActionForRequestCode() {
        GoogleFitHelper.sendWorkout(this, "Workout - home", 1400000, true);
    }
}
