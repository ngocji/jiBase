package com.jibase.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

typealias SuspendActionUnit = suspend CoroutineScope.() -> Unit

fun CoroutineScope.runAsync(
    context: CoroutineDispatcher = Dispatchers.IO,
    onComplete: (suspend CoroutineScope.(isCompleted: Boolean) -> Unit)? = null,
    vararg actions: SuspendActionUnit
): Job {
    return launch(context = context) {
        val deferredJobs = actions.map { action ->
            async {
                action()
            }
        }
        var isCompleted = false
        try {
            deferredJobs.awaitAll()
            isCompleted = true
        } catch (_: Exception) {

        } finally {
            if (onComplete != null) onComplete(isCompleted)
        }
    }
}

fun CoroutineScope.runCoroutine(
    context: CoroutineDispatcher = Dispatchers.IO,
    action: SuspendActionUnit
) = launch(context = context) { action() }


fun FragmentActivity.runAsync(
    context: CoroutineDispatcher = Dispatchers.IO,
    onComplete: (suspend CoroutineScope.(isCompleted: Boolean) -> Unit)? = null,
    vararg actions: SuspendActionUnit
) = lifecycle.coroutineScope.runAsync(context, onComplete, *actions)

fun FragmentActivity.runCoroutine(
    context: CoroutineDispatcher = Dispatchers.IO,
    action: SuspendActionUnit
) = lifecycle.coroutineScope.runCoroutine(context, action)


fun Fragment.runAsync(
    context: CoroutineDispatcher = Dispatchers.IO,
    onComplete: (suspend CoroutineScope.(isCompleted: Boolean) -> Unit)? = null,
    vararg actions: SuspendActionUnit
) = viewLifecycleOwner.lifecycleScope.runAsync(context, onComplete, *actions)

fun Fragment.runCoroutine(
    context: CoroutineDispatcher = Dispatchers.IO,
    action: SuspendActionUnit
) = viewLifecycleOwner.lifecycleScope.runCoroutine(context, action)

fun ViewModel.runAsync(
    context: CoroutineDispatcher = Dispatchers.IO,
    onComplete: (suspend CoroutineScope.(isCompleted: Boolean) -> Unit)? = null,
    vararg actions: SuspendActionUnit
) = viewModelScope.runAsync(context, onComplete, *actions)

fun ViewModel.runCoroutine(
    context: CoroutineDispatcher = Dispatchers.IO,
    action: SuspendActionUnit
) = viewModelScope.runCoroutine(context, action)