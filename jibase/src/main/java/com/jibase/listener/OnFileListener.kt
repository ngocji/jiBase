package com.jibase.listener

abstract class OnFileListener {
    fun success(path: String){}
    fun error(e: String){}
}