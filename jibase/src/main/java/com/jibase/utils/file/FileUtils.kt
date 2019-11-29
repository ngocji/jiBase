package com.jibase.utils.file

import com.jibase.BaseApp
import com.jibase.listener.OnFileListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

/**
 * Save file in sdcard
 * @param data: bytearray of the file
 * @param path: path want save
 * @param name: name want save ex: file1.png
 * @param isCopyIfExists: true: rename when exists, false: override file
 * @param callback: callback
 * @return disposable observable
 */
fun saveFile(data: ByteArray,
             path: String,
             name: String,
             isCopyIfExists: Boolean = false,
             callback: OnFileListener? = null): Disposable {
    return Observable.create<Pair<Boolean, String>> {
        try {
            var file = File("$path$name")
            file.parentFile.mkdirs()
            if (file.exists()) {
                if (isCopyIfExists) {
                    file = File("$path${createCopyName(name)}")
                } else {
                    file.delete()
                }
            }
            val out = FileOutputStream(file)
            out.write(data)
            out.flush()
            out.close()
            it.onNext(Pair(true, file.path))
        } catch (e: Exception) {
            it.onNext(Pair(false, e.toString()))
        }
    }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.first) {
                    callback?.success(it.second)
                } else {
                    callback?.error(it.second)
                }
            }
}


fun copyFile(sourceFile: File, destFile: File): File {
    sourceFile.inputStream().use { input ->
        destFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return destFile
}

/**
 * Override name when name exists
 * @param name: name of the file
 * @return name_{System.currentTimeMillis()}
 */
private fun createCopyName(name: String): String {
    return if (name.contains("")) {
        val spName = name.split("").filter { it.isNotEmpty() }
        "${spName.first()}_${System.currentTimeMillis()}.${spName.last()}"
    } else {
        "${name}_${System.currentTimeMillis()}.png"
    }
}

@Throws
fun getTextFromAsset(path: String): String {
    return BaseApp.instance.assets.open(path).bufferedReader().use { it.readText() }
}