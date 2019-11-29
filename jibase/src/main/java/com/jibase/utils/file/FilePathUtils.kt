package com.jibase.utils.file

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

object FilePathUtils {
    private const val NO_PATH = "No File Selected!"
    private const val PREFIX_ASSET = "file:///android_asset/"


    fun getRealPath(context: Context, path: String): String {
        return if (isAssetFile(path)) path.replace(PREFIX_ASSET, "") else return getRealPath(context, Uri.parse(path))
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    fun getRealPath(context: Context, uri: Uri): String {
        // check here to KITKAT or new version
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return (Environment.getExternalStorageDirectory().toString() + "/"
                            + split[1])
                }
            } else if (isDownloadsDocument(uri)) {
                try {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(id))

                    return getDataColumn(context, contentUri, null, null)
                } catch (e: Exception) {
                    val uriFormat = Uri.decode(uri.toString().replace("content://com.android.providers.downloads.documents/", ""))
                    if (uriFormat.contains(":")) {
                        val sp = uriFormat.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        return sp[sp.size - 1]
                    } else {
                        return NO_PATH
                    }
                }

            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection,
                        selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme ?: "nullSchema", ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment ?: uri.toString() else getDataColumn(context, uri, null, null)

        } else if ("file".equals(uri.scheme ?: "nullSchema", ignoreCase = true)) {
            return uri.path ?: uri.toString()
        }// File
        // MediaStore (and general)

        return NO_PATH
    }


    /**
     * Get the value of the data column for this Uri. This is <span id="IL_AD2" class="IL_AD">useful</span> for MediaStore Uris, and other file-based
     * ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(context: Context, uri: Uri?,
                      selection: String?, selectionArgs: Array<String>?): String {
        if (uri != null) {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri, projection,
                        selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
        }
        return NO_PATH
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri
                .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri
                .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri
                .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri
                .authority
    }

    fun isAssetFile(path: String): Boolean {
        return path.contains(PREFIX_ASSET)
    }

}