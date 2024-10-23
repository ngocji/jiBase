package com.jibase.retriever

import android.content.Context
import com.jibase.helper.AssetsUtils
import com.jibase.helper.GsonManager
import com.jibase.utils.Log
import java.lang.reflect.Type

abstract class BaseAssetDataRetriever<T>(
    open val context: Context,
    open val type: Type,
    private val path: String,
) {
    private var data: T? = null

    open suspend fun getLocal(): T? {
        return try {
            GsonManager.fromJson(
                AssetsUtils.readTextFromAsset(
                    context = context,
                    path = path
                ), type
            )
        } catch (e: Exception) {
            Log.e("error read text from asset: $data --> $type")
            null
        }
    }

    suspend fun getData(): T? {
        return when (data) {
            null -> getLocal().apply {
                data = this
            }

            else -> data ?: throw NullPointerException("Null data")
        }
    }
}