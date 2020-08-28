package comx.y.z.kotlinbase

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jibase.extensions.inflate
import com.jibase.iflexible.adapter.FlexibleAdapter
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleItem
import com.jibase.iflexible.viewholder.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_test.view.*

class TestHolder(val item: String) : AbstractFlexibleItem<TestHolder.ViewHolder>() {
    override fun bindViewHolder(adapter: FlexibleAdapter<*>, holder: RecyclerView.ViewHolder, position: Int, payloads: List<*>) {
        holder.itemView.text.text = item
    }

    override fun createViewHolder(parent: ViewGroup, adapter: FlexibleAdapter<*>): ViewHolder {
        return ViewHolder(parent.inflate(getLayoutRes()), adapter)
    }

    override fun getLayoutRes(): Int = R.layout.item_test

    class ViewHolder(preItemView: View, adapter: FlexibleAdapter<*>, isStickyHeader: Boolean = false) : FlexibleViewHolder(preItemView, adapter, isStickyHeader) {

    }
}