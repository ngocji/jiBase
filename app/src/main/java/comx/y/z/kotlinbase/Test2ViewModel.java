package comx.y.z.kotlinbase;

import com.jibase.iflexible.items.interfaceItems.IFlexible;
import com.jibase.livedata.ILiveData;
import com.jibase.ui.BaseViewModel;
import java.util.List;

public class Test2ViewModel extends BaseViewModel {
    public ILiveData<List<IFlexible<?>>> test = new ILiveData<>();
}
