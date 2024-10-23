package com.jibase.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SimpleViewPagerAdapter<T>(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val items: List<T>,
    private val onCreateItem: (item: T) -> Fragment
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return onCreateItem.invoke(items[position])
    }
}


fun <T> Fragment.newSimpleViewPager(
    items: List<T>,
    onCreateItem: (item: T) -> Fragment
): FragmentStateAdapter {
    return SimpleViewPagerAdapter<T>(
        childFragmentManager,
        viewLifecycleOwner.lifecycle,
        items,
        onCreateItem
    )
}

fun <T> FragmentActivity.newSimpleViewPager(
    items: List<T>,
    onCreateItem: (item: T) -> Fragment
): FragmentStateAdapter {
    return SimpleViewPagerAdapter<T>(
        supportFragmentManager,
        lifecycle,
        items,
        onCreateItem
    )
}