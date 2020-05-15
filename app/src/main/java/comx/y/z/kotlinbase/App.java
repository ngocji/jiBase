package comx.y.z.kotlinbase;

import com.jibase.BaseApp;

import org.jetbrains.annotations.NotNull;
import org.koin.core.module.Module;

import java.util.List;

public class App extends BaseApp {
    @Override
    public void initAppModule(@NotNull List<Module> modules) {
        super.initAppModule(modules);
        modules.add(Di.getVm());
    }
}
