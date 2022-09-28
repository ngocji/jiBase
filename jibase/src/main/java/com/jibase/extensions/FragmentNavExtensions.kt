@file:JvmName("FragmentExtensions")

package com.jibase.extensions

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


@Suppress("UNCHECKED_CAST")
fun <F : Fragment> AppCompatActivity.getFragment(fragmentClass: Class<F>): F? {
    val navHostFragment = this.supportFragmentManager.fragments.first() as NavHostFragment

    navHostFragment.childFragmentManager.fragments.forEach {
        if (fragmentClass.isAssignableFrom(it.javaClass)) {
            return it as F
        }
    }

    return null
}

fun <T> Fragment.getNavigationResult(
    key: String = "result",
    destinationId: Int? = null
): LiveData<T>? {
    val backStack = destinationId?.let { findNavController().getBackStackEntry(it) }
        ?: findNavController().currentBackStackEntry

    return backStack?.savedStateHandle?.getLiveData(key)
}

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.setNavigationResult(result: T, destinationId: Int, key: String = "result") {
    try {
        findNavController().getBackStackEntry(destinationId).savedStateHandle.set(key, result)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

inline fun <T> Fragment.observeNavigationResultOnce(
    key: String = "result",
    crossinline block: (T) -> (Unit)
) =
    observe(getNavigationResult<T>(key)) { removeNavigationResult<T>(key);block.invoke(it) }

inline fun <T> Fragment.observeDialogNavigationResultOnce(
    destinationId: Int,
    key: String = "result",
    crossinline block: (T) -> (Unit)
) =
    observe(getNavigationResult<T>(key, destinationId)) {
        removeNavigationResult<T>(
            key,
            destinationId
        );block.invoke(it)
    }

fun <T> Fragment.setCurrentNavigationResult(key: String = "result", result: T?) {
    findNavController().currentBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.setPreviousNavigationResult(key: String = "result", result: T?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.getCurrentNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.getPreviousNavigationResult(key: String = "result") =
    findNavController().previousBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.removeCurrentNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)

fun <T> Fragment.removePreviousNavigationResult(key: String = "result") =
    findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)


fun <T> Fragment.removeNavigationResult(key: String = "result", destinationId: Int? = null) {
    val backStack = destinationId?.let { findNavController().getBackStackEntry(it) }
        ?: findNavController().currentBackStackEntry
    backStack?.savedStateHandle?.remove<T>(key)
}

fun Fragment.navigateUp(): Boolean {
    return NavHostFragment.findNavController(this).navigateUp()
}

fun Fragment.popBackTo(destinationId: Int, inclusive: Boolean = false): Boolean {
    return NavHostFragment.findNavController(this).popBackStack(destinationId, inclusive)
}

fun Fragment.popBack(): Boolean {
    return NavHostFragment.findNavController(this).popBackStack()
}

fun FragmentActivity.onBackPressedOverride(func: () -> Unit) {
    this.onBackPressedDispatcher.addCallback(
        this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                func()
            }
        })
}

fun Fragment.onBackPressedOverride(func: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                func.invoke()
            }
        })
}

fun Fragment.observeOnDestroy(action: () -> Unit) {
    viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                action.invoke()
            }
        })
    }
}

fun Fragment.observeOnResume(action: () -> Unit) {
    viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                action.invoke()
            }
        })
    }
}

fun Fragment.observeOnPause(action: () -> Unit) {
    viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                action.invoke()
            }
        })
    }
}


// region support for java
fun FragmentActivity.onBackPressOverride(func: Runnable) {
    onBackPressedOverride {
        func.run()
    }
}

fun Fragment.onBackPressOverride(func: Runnable) {
    onBackPressedOverride {
        func.run()
    }
}

fun Fragment.observeOnDestroy(runnable: Runnable) {
    observeOnDestroy { runnable.run() }
}

fun Fragment.observeOnResume(runnable: Runnable) {
    observeOnResume { runnable.run() }
}

fun Fragment.observeOnPause(runnable: Runnable) {
    observeOnPause { runnable.run() }
}

fun <T> Fragment.observeNavigationResultOnce(key: String, observe: Observer<T>) {
    observe(getNavigationResult<T>(key)) {
        removeNavigationResult<T>(key)
        observe.onChanged(it)
    }
}

fun <T> Fragment.observeDialogNavigationResultOnce(
    key: String,
    destinationId: Int,
    observe: Observer<T>
) {
    observe(getNavigationResult<T>(key, destinationId)) {
        removeNavigationResult<T>(
            key,
            destinationId
        );
        observe.onChanged(it)
    }
}