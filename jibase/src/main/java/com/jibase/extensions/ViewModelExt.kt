package com.jibase.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

fun <T> Flow<T>.cachedIn(scope: CoroutineScope) = this
    .shareIn(
        scope,
        replay = 1,
        started = SharingStarted.Lazily
    )