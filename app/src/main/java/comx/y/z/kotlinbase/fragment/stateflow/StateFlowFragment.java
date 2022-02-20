package comx.y.z.kotlinbase.fragment.stateflow;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jibase.ui.base.BaseFragment;

import comx.y.z.kotlinbase.R;
import comx.y.z.kotlinbase.databinding.FragmentStateFlowBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StateFlowFragment extends BaseFragment {
    FragmentStateFlowBinding binding;
    @Override
    public void onViewReady(@Nullable Bundle bundle) {
        binding = FragmentStateFlowBinding.bind(getView());
    }
}
