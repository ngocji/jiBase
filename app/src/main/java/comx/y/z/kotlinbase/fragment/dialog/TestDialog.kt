package comx.y.z.kotlinbase.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import com.jibase.ui.dialog.BaseBottomDialog
import com.jibase.ui.dialog.BaseDialog
import comx.y.z.kotlinbase.R

class TestDialog : BaseBottomDialog(R.layout.test_dialog, R.style.AppTheme) {
    override fun isShowFullDialog(): Boolean {
        return true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
    }
}