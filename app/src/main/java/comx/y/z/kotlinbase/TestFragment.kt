package comx.y.z.kotlinbase

import android.os.Bundle
import com.jibase.anotation.ViewInflate
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.ui.base.SimpleBaseFragment
import com.jibase.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_test_1.*

@ViewInflate(layout = R.layout.fragment_test_1, enableBackPressed = true)
class TestFragment : SimpleBaseFragment(){
    override fun onViewReady(savedInstanceState: Bundle?) {
        val list = mutableListOf<TestHolder>()
        for (i in 0..10){
            list.add(TestHolder("item: $i"))
        }

       val adater = FlexibleAdapter<TestHolder>(list)
        recyclerView.adapter = adater
    }

    override fun onBackPressed() {
        ToastUtils.showText(requireContext(), "Back")
    }
}