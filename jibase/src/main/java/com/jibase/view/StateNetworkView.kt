package com.jibase.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.jibase.R
import com.jibase.databinding.LayoutStateNetworkBinding
import com.jibase.extensions.gone
import com.jibase.extensions.visible

@Suppress("MemberVisibilityCanBePrivate", "JoinDeclarationAndAssignment")
class StateNetworkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : FrameLayout(context, attrs, def) {


    enum class NetworkType(val tagRes: Int) {
        LOADING(R.string.tag_progress),
        ERROR(R.string.tag_error),
        SUCCESS(R.string.tag_content),
        EMPTY(R.string.tag_empty);

        companion object {
            fun safe(context: Context, tag: Any?): NetworkType? {
                return entries.find { context.getString(it.tagRes) == tag }
            }
        }
    }

    // true to show progress, false to show refresh
    private var onClickListener: ((NetworkType) -> Unit)? = null

    var currentState = NetworkType.LOADING

    private val binding: LayoutStateNetworkBinding

    init {
        binding = LayoutStateNetworkBinding.inflate(LayoutInflater.from(context), this)

        // init arts
        attrs?.also {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.StateNetworkView, 0, 0)

            val enablePullToRefresh = typedArray.getBoolean(
                R.styleable.StateNetworkView_state_enable_pull_to_refresh,
                false
            )

            binding.swipeRefreshLayout.isEnabled = enablePullToRefresh

            val layoutProgressRes =
                typedArray.getResourceId(R.styleable.StateNetworkView_state_progress_layout, -1)
            replaceView(NetworkType.LOADING, layoutProgressRes)

            val layoutErrorRes =
                typedArray.getResourceId(R.styleable.StateNetworkView_state_error_layout, -1)
            replaceView(NetworkType.ERROR, layoutErrorRes)

            val layoutEmptyRes =
                typedArray.getResourceId(R.styleable.StateNetworkView_state_empty_layout, -1)

            if (layoutEmptyRes > 0) {
                replaceView(NetworkType.EMPTY, layoutEmptyRes)
            } else {
                findTag(NetworkType.EMPTY)?.also { view ->
                    val icon = typedArray.getDrawable(R.styleable.StateNetworkView_state_empty_icon)
                    if (icon != null) {
                        view.findViewById<ImageView>(R.id.imageEmpty).apply {
                            visible()
                            setImageDrawable(icon)
                        }
                    }

                    val text = typedArray.getString(R.styleable.StateNetworkView_state_empty_text)
                    if (text?.isNotEmpty() == true) {
                        view.findViewById<TextView>(R.id.tvEmpty).text = text
                    }
                }
            }

            typedArray.recycle()
        }

        onClickButton(NetworkType.ERROR)
        onClickButton(NetworkType.EMPTY)

        binding.swipeRefreshLayout.setOnRefreshListener {
            val temp = currentState
            updateUI(NetworkType.LOADING)
            onClickListener?.invoke(temp)
        }
    }

    fun setCallBack(onClickListener: ((NetworkType) -> Unit)?): StateNetworkView {
        this.onClickListener = onClickListener
        return this
    }

    fun updateUI(networkType: NetworkType) {
        if (currentState == networkType) return
        currentState = networkType
        when (networkType) {
            NetworkType.LOADING -> {
                updateViewState(hideContent = false, defaultVisible = true)
                if (!binding.swipeRefreshLayout.isRefreshing) findTag(NetworkType.LOADING)?.visible()
            }

            NetworkType.ERROR -> {
                updateViewState()
                findTag(NetworkType.ERROR)?.visible()
                binding.swipeRefreshLayout.isRefreshing = false
            }

            NetworkType.SUCCESS -> {
                updateViewState(hideContent = false)
                binding.swipeRefreshLayout.isRefreshing = false
            }

            NetworkType.EMPTY -> {
                updateViewState()
                findTag(NetworkType.EMPTY)?.visible()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    fun updateUI(tag: Any) {
        updateViewState()
        findTag(tag)?.visible()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    @SuppressLint("ResourceType")
    fun addTag(tag: Any, @LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        findTag(tag)?.also {
            binding.flState.removeView(it)
        }

        val layout = LayoutInflater.from(context).inflate(layoutId, null)
        layout.tag = tag
        binding.flState.addView(layout)
    }

    private fun updateViewState(
        hideTag: Boolean = true,
        hideContent: Boolean = true,
        defaultVisible: Boolean = true
    ) {
        binding.flState.forEach { view ->
            when {
                hideTag && view.tag != null -> view.gone()
                hideContent && view.tag == null -> view.gone()
                else -> {
                    view.isVisible = defaultVisible
                }
            }
        }
    }

    private fun findTag(tag: Any): View? {
        return binding.flState.findViewWithTag(if (tag is NetworkType) context.getString(tag.tagRes) else tag)
    }

    @SuppressLint("ResourceType")
    fun replaceView(type: NetworkType, @LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        findTag(type)?.also {
            binding.flState.removeView(it)
        }

        val layout = LayoutInflater.from(context).inflate(layoutId, null)
        layout.tag = context.getString(type.tagRes)
        binding.flState.addView(layout)
        onClickButton(type)
    }

    private fun onClickButton(type: NetworkType) {
        findTag(type)?.findViewWithTag<View>(context.getString(R.string.tag_button))
            ?.setOnClickListener {
                if (binding.swipeRefreshLayout.isRefreshing || currentState == NetworkType.LOADING) return@setOnClickListener
                updateUI(NetworkType.LOADING)
                onClickListener?.invoke(type)
            }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child?.javaClass?.name == "androidx.swiperefreshlayout.widget.SwipeRefreshLayout") {
            super.addView(child, index, params)
        } else {
            val isNetworkType = NetworkType.safe(context, child?.tag) != null
            if (isNetworkType) {
                binding.flState.addView(child)
            } else {
                binding.flState.addView(child, 0, params)
            }
        }
    }
}