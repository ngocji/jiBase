package comx.y.z.kotlinbase;

import android.os.Bundle;

import com.jibase.anotation.BindingInfo;
import com.jibase.iflexible.adapter.BindFlexibleAdapter;
import com.jibase.iflexible.items.interfaceItems.IFlexible;
import com.jibase.ui.mvvm.BindActivity;
import com.jibase.utils.UI;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import comx.y.z.kotlinbase.databinding.ActivityMainBinding;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;


@BindingInfo(layout = R.layout.activity_main, viewModel = Test2ViewModel.class)
public class TestActivity extends BindActivity<Test2ViewModel> {

    private BindFlexibleAdapter<IFlexible<?>> adapter;

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        adapter = (BindFlexibleAdapter<IFlexible<?>>) new BindFlexibleAdapter<>(getViewModel().test, false)
        .setStickyHeaders(true)
        .setDisplayHeadersAtStartUp(true);

        ((ActivityMainBinding) getBinding()).recyclerView.setAdapter(adapter);

        List<IFlexible<?>> holders = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i%5 == 0){
                holders.add(new TestHeader("Header"));
            }
            holders.add(new TestHolder("data " + i));
        }

        getViewModel().test.post(holders);
    }
}
