package com.jibase.listener

open class OnFileListener {
    open fun success(path: String) {}
    open fun error(e: String) {}
}