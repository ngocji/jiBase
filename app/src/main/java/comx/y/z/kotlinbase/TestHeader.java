package comx.y.z.kotlinbase;

import androidx.recyclerview.widget.RecyclerView;

import com.jibase.iflexible.adapter.FlexibleAdapter;
import com.jibase.iflexible.items.binding.BindFlexibleHeaderItem;
import com.jibase.iflexible.viewholder.binding.BindFlexibleViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import comx.y.z.kotlinbase.databinding.ItemHeaderBinding;

public class TestHeader extends BindFlexibleHeaderItem {
    private String title;

    public TestHeader(String title) {
        this.title = title;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_header;
    }

    @Override
    public void bindViewHolder(@NotNull FlexibleAdapter<?> adapter, @NotNull RecyclerView.ViewHolder holder, int position, @NotNull List<?> payloads) {
        ItemHeaderBinding binding = (ItemHeaderBinding) ((BindFlexibleViewHolder) holder).getBinding();
        binding.text.setText(title);
    }
}
