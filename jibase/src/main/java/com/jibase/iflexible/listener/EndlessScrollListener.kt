package com.jibase.iflexible.listener

interface EndlessScrollListener {
    /**
     * No more data to load.
     *
     * This method is called if any limit is reached (**targetCount** or **pageSize**
     * must be set) AND if new data is <u>temporary</u> unavailable (ex. no connection or no
     * new updates remotely). If no new data, a [FlexibleAdapter.notifyItemChanged]
     * with a payload [Payload.NO_MORE_LOAD] is triggered on the *progressItem*.
     *
     * @param newItemsSize the last size of the new items loaded
     */
    fun noMoreLoad(newItemsSize: Int)

    /**
     * Loads more data.
     *
     * Use `lastPosition` and `currentPage` to know what to load next.
     * `lastPosition` is the count of the main items without Scrollable Headers.
     *
     * @param lastPosition the position of the last main item in the adapter
     * @param currentPage  the current page
     */
    fun onLoadMore(lastPosition: Int, currentPage: Int)
}