package com.jibase.iflexible.items.abstractItems

import com.jibase.extensions.has
import com.jibase.iflexible.items.interfaceItems.IExpandable
import com.jibase.iflexible.items.interfaceItems.IFlexible
import com.jibase.iflexible.viewholder.FlexibleExpandableViewHolder

abstract class AbstractFlexibleExpandItem<VH : FlexibleExpandableViewHolder, S : IFlexible<*>>(private var preSubItems: MutableList<S> = mutableListOf()) : AbstractFlexibleItem<VH>(), IExpandable<VH, S> {
    /* Flags for FlexibleAdapter */
    open var isExpand = false

    /*--------------------*/
    /* EXPANDABLE METHODS */
    /*--------------------*/


    override fun isExpanded() = isExpand

    override fun setExpanded(expanded: Boolean) {
        isExpand = expanded
    }

    override fun getExpansionLevel() = 0

    /*-------------------*/
    /* SUB ITEMS METHODS */
    /*-------------------*/

    fun hasSubItems(): Boolean {
        return preSubItems.isNotEmpty()
    }

    fun setSubItems(subItems: List<S>): AbstractFlexibleExpandItem<VH, S> {
        this.preSubItems = subItems.toMutableList()
        return this
    }

    fun addSubItems(position: Int, subItems: List<S>): AbstractFlexibleExpandItem<VH, S> {
        if (preSubItems has position) {
            preSubItems.addAll(position, subItems)
        } else {
            this.preSubItems.addAll(subItems)
        }
        return this
    }

    fun addSubItem(subItem: S): AbstractFlexibleExpandItem<VH, S> {
        preSubItems.add(subItem)
        return this
    }

    fun addSubItem(position: Int, subItem: S): AbstractFlexibleExpandItem<VH, S> {
        if (preSubItems has position) {
            preSubItems.add(position, subItem)
        } else {
            addSubItem(subItem)
        }
        return this
    }

    fun getSubItemsCount(): Int {
        return preSubItems.size
    }


    fun getSubItem(position: Int): S? {
        return if (preSubItems has position) preSubItems[position]
        else null
    }

    fun getSubItemPosition(subItem: S): Int {
        return preSubItems.indexOf(subItem)
    }

    operator fun contains(subItem: S): Boolean {
        return preSubItems.contains(subItem)
    }

    fun removeSubItem(item: S?): Boolean {
        return item != null && preSubItems.remove(item)
    }

    fun removeSubItems(subItems: List<S>?): Boolean {
        return subItems != null && this.preSubItems.removeAll(subItems)
    }

    fun removeSubItem(position: Int): Boolean {
        if (preSubItems has position) {
            preSubItems.removeAt(position)
            return true
        }
        return false
    }

}