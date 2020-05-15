package comx.y.z.kotlinbase;

import androidx.lifecycle.LiveData;

import com.jibase.livedata.ILiveData;
import com.jibase.ui.mvvm.BindViewModel;

public class Test2ViewModel extends BindViewModel {
    public ILiveData<Boolean> test = new ILiveData<>();
}
