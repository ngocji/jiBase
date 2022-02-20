package comx.y.z.kotlinbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jibase.anotation.Inflate;
import com.jibase.ui.dialog.BaseDialog;

import comx.y.z.kotlinbase.databinding.DialogTestBinding;

public class TestDialog extends BaseDialog {
    @Inflate
    DialogTestBinding binding;

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        binding.tv.setOnClickListener(v -> {
            dismissAllowingStateLoss();
        });
    }
}
