package comx.y.z.kotlinbase;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.jibase.extensions.BindingExtensions;
import com.jibase.iflexible.adapter.FlexibleAdapter;
import com.jibase.iflexible.items.abstractItems.AbstractFlexibleItem;
import com.jibase.iflexible.items.binding.BindFlexibleItem;
import com.jibase.iflexible.viewholder.binding.BindFlexibleViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import comx.y.z.kotlinbase.databinding.ItemBinding;

public class TestHolder extends BindFlexibleItem {
    private String data;

    public TestHolder(String data) {
        this.data = data;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item;
    }


    @Override
    public void bindViewHolder(@NotNull FlexibleAdapter<?> adapter, @NotNull RecyclerView.ViewHolder holder, int position, @NotNull List<?> payloads) {
        ItemBinding binding = (ItemBinding) ((BindFlexibleViewHolder) holder).getBinding();
        binding.text.setText(data);
    }
}
