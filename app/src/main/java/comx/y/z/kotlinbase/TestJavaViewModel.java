package comx.y.z.kotlinbase;

import androidx.lifecycle.ViewModel;

import com.jibase.pref.SharePref;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TestJavaViewModel extends ViewModel {
    SharePref sharePref;

    @Inject
    public TestJavaViewModel(SharePref sharePref) {
        this.sharePref = sharePref;
    }

    public void count() {
        sharePref.put("x", 1);
    }
}
