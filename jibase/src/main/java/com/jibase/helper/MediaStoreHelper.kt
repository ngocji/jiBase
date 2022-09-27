package com.jibase.helper

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jibase.permission.Permission
import com.jibase.permission.PermissionsHelper
import com.jibase.utils.Log
import com.jibase.utils.copyFile
import com.jibase.utils.getMimeType
import java.io.File


object MediaStoreHelper {
    fun insert(data: Data): Uri? {
        return try {
            val resultUri: Uri?
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, data.name)
            values.put(
                MediaStore.MediaColumns.MIME_TYPE,
                data.mimeType.ifBlank { getMimeType(data.file) }
            )
            values.put(MediaStore.MediaColumns.TITLE, data.name)

            resultUri = if (isUserRelativePath()) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, data.relativeSaveFolder)
                data.context.contentResolver.insert(
                    MediaStore.Files.getContentUri("external"),
                    values
                )
            } else {
                if (!data.copyToNewPath && data.saveFile != null) {
                    values.put(MediaStore.MediaColumns.DATA, data.saveFile?.absolutePath)
                    copyFile(data.file, data.saveFile ?: return null)
                }
                data.context.contentResolver.insert(
                    if (data.mimeType.contains("video", ignoreCase = true)) MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    else MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
            }
            if (resultUri == null) {
                throw NullPointerException("error")
            }

            if (data.copyToNewPath) {
                val outputStream = data.context.contentResolver.openOutputStream(resultUri)
                    ?: throw NullPointerException("Error insert media")
                copyFile(data.file, outputStream)
            }

            if (data.deleteFileAfterCopy) {
                data.file.delete()
            }
            resultUri
        } catch (e: Exception) {
            null
        }
    }

    fun isUserRelativePath() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q


    data class Data(
        var context: Context,
        var name: String,
        var file: File,
        var mimeType: String = "",
        var relativeSaveFolder: String = "",
        var copyToNewPath: Boolean = true,
        var deleteFileAfterCopy: Boolean = false,
        var saveFile: File? = null
    )


    fun deleteMedia(context: Context, uri: Uri): Boolean {
        return try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                @Suppress("DEPRECATION") val projection = arrayOf(MediaStore.MediaColumns.DATA)
                context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val filePath = cursor.getString(0)
                        File(filePath).delete()
                    }
                }
            }
            val result = context.contentResolver.delete(uri, null, null)
            Log.e("deleteMedia: $result")
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun createDesiredFileBeforeQ(toRelativePath: String): File {
        @Suppress("DEPRECATION") var desiredFile =
            File(getExternalStorageDirectory(), toRelativePath)
        val dir = desiredFile.parentFile
        val extensionWithDot =
            if (desiredFile.extension.isBlank()) "" else ".${desiredFile.extension}"
        val nameWithoutExtension = desiredFile.nameWithoutExtension
        var count = 0

        dir?.mkdirs()
        while (desiredFile.exists()) {
            count++
            desiredFile = File(dir, "$nameWithoutExtension ($count)$extensionWithDot")
        }
        desiredFile.createNewFile()
        return desiredFile
    }


    fun pickGallery(target: Fragment, requestCode: Int, onDeny: (List<Permission>) -> Unit) {
        requestStoragePermission(target, {
            try {
                val intent = obtainImageIntent()
                target.startActivityForResult(intent, requestCode)
            } catch (exception: ActivityNotFoundException) {

            }
        }, onDeny)
    }

    fun pickGallery(
        target: FragmentActivity,
        requestCode: Int,
        onDeny: (List<Permission>) -> Unit
    ) {
        requestStoragePermission(target, {
            try {
                val intent = obtainImageIntent()
                target.startActivityForResult(intent, requestCode)
            } catch (exception: ActivityNotFoundException) {

            }
        }, onDeny)
    }

    fun pickFile(
        target: Fragment,
        requestCode: Int,
        mimeType: String,
        onDeny: (List<Permission>) -> Unit
    ) {
        requestStoragePermission(target, {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = mimeType
            val chooser = Intent.createChooser(intent, "Select file")
            target.startActivityForResult(chooser, requestCode)
        }, onDeny)
    }

    fun pickFile(
        target: FragmentActivity,
        requestCode: Int,
        mimeType: String,
        onDeny: (List<Permission>) -> Unit
    ) {
        requestStoragePermission(target, {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = mimeType
            val chooser = Intent.createChooser(intent, "Select file")
            target.startActivityForResult(chooser, requestCode)
        }, onDeny)
    }

    private fun requestStoragePermission(
        target: Fragment,
        onGrant: () -> Unit,
        onDeny: (List<Permission>) -> Unit
    ) {
        PermissionsHelper.with(target)
            .request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .onGrant(onGrant)
            .onDeny(onDeny)
            .execute()
    }

    private fun requestStoragePermission(
        target: FragmentActivity,
        onGrant: () -> Unit,
        onDeny: (List<Permission>) -> Unit
    ) {
        PermissionsHelper.with(target)
            .request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .onGrant(onGrant)
            .onDeny(onDeny)
            .execute()
    }

    private fun obtainImageIntent(): Intent = Intent.createChooser(
        Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }, "Pick files: "
    )

    fun rename(context: Context, uri: Uri, newName: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, newName)
            }

            context.contentResolver.update(uri, values, null, null)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun requestDelete(
        context: Context,
        launcher: ActivityResultLauncher<IntentSenderRequest>,
        uris: List<Uri>
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = MediaStore.createDeleteRequest(
                context.contentResolver,
                uris
            )
            val intentSenderRequest = IntentSenderRequest.Builder(intent.intentSender).build()
            launcher.launch(intentSenderRequest)
            false
        } else {
            true
        }
    }

    fun requestWrite(
        context: Context,
        launcher: ActivityResultLauncher<IntentSenderRequest>,
        uris: List<Uri>
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = MediaStore.createWriteRequest(
                context.contentResolver,
                uris
            )
            val intentSenderRequest = IntentSenderRequest.Builder(intent.intentSender).build()
            launcher.launch(intentSenderRequest)
            false
        } else {
            true
        }
    }
}