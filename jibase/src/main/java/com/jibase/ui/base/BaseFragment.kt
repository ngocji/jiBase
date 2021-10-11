package com.jibase.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jibase.anotation.InflateHelper
import com.jibase.anotation.ViewInflate
import com.jibase.ui.BaseViewModel
import io.reactivex.disposables.Disposable

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    open val viewInflate: ViewInflate by lazy { InflateHelper.getAnnotation(this) }
    open val viewModel: VM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(viewInflate.viewModel.java) as VM
    }

    private val backPressedCallback by lazy {
        object : OnBackPressedCallback(viewInflate.enableBackPressed) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initView(inflater, container)
    }

    open fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return if (this.viewInflate.layout != ID_NULL) {
            inflater.inflate(viewInflate.layout, container, false)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressed()
        onViewListener()
        onViewReady(savedInstanceState)
    }

    abstract fun onViewReady(savedInstanceState: Bundle?)

    open fun onViewListener() {
        // free implement
    }

    open fun onBackPressed() {
    }

    fun registerBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }

    fun removeBackPressed() {
        backPressedCallback.remove()
    }

    fun Disposable.putToComposite() {
        viewModel.compositeDisposable.add(this)
    }
}

typealias SimpleBaseFragment = BaseFragment<BaseViewModel>