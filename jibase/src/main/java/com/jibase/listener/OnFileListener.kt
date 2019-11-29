package com.jibase.listener

interface OnFileListener {
    fun success(path: String){}
    fun error(e: String){}
}