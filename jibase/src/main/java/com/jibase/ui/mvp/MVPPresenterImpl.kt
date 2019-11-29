package com.jibase.ui.mvp

abstract class MVPPresenterImpl<V : MVPView>( val mView: V) : MVPPresenter<V>