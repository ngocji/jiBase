package comx.y.z.kotlinbase

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.ui.dialog.BaseCallBackDialog
import kotlinx.android.synthetic.main.dialog_test.*

@ViewInflate(layout = R.layout.dialog_test)
class TestDialog : BaseCallBackDialog<TestDialog.Callback>() {
    companion object{
        fun newInstance(callback: Callback): TestDialog {
            val ins = TestDialog()
            ins.setCallBack(callback)
            return ins
        }
    }


    override fun onViewReady(savedInstanceState: Bundle?) {
        button.setOnClickListener {
            getCallBack()?.onClicked()
        }
    }

    interface Callback {
        fun onClicked()
    }
}