package com.jibase.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.jibase.R
import com.jibase.utils.ResourceUtils.getString
import com.jibase.utils.ToastUtils.showText
import org.koin.core.KoinComponent

object IntentUtils : KoinComponent {

    /**
     *  go to google play store
     *  @param context: context
     *  @param pkg: package name
     */
    fun goToStore(context: Context, pkg: String) {
        val uri = Uri.parse("market://details?id=$pkg")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(goToMarket)
        } catch (e: Exception) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$pkg")
                )
            )
        }
    }

    /**
     * Share app
     * @param context: Context
     * @param pkg: package name
     * @param text: string description
     */
    fun shareApp(context: Context, pkg: String, text: String = "") {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(
                Intent.EXTRA_TEXT,
                if (text.isEmpty())
                    "Visit to: https://play.google.com/store/apps/details?id=$pkg"
                else text
            )
            type = "text/plain"
        }
        try {
            context.startActivity(sendIntent)
        } catch (e: Exception) {
            Log.e("ERROR SHARE APPS: $e")
            showText(context, "You don't have any matching application!")
        }
    }

    /**
     * Open to app in android
     * @param context:Context
     * @param pkg: package name
     * @param error: message when package name not exists
     */
    fun goToApps(context: Context, pkg: String, error: String) {
        try {
            val launcherIntent = context.packageManager.getLaunchIntentForPackage(pkg)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(launcherIntent)
        } catch (e: Exception) {
            goToStore(context, pkg)
            showText(context, error)
        }

    }

    /**
     * GO to url
     * @param context
     * @param url
     * @param packageName
     */
    fun goToUrl(context: Context, url: String, packageName: String = "") {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            if (packageName.isNotEmpty()) setPackage(packageName)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            showText(context, "No apps to open!")
        }
    }

    /**
     * Share text from intent
     * @param context
     * @param mess
     * @param subject: Subject title
     */
    fun shareText(context: Context, mess: String, subject: String = "", packageName: String = "") {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, mess)
            if (packageName.isNotEmpty()) setPackage(packageName)
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Share use with: "))
        } catch (e: Exception) {
            showText(context, getString(R.string.error_share))
        }
    }

    /**
     * Share image from intent
     * @param context: Context
     * @param bitmap: Bitmap src require not null
     * @param error: Message when no intent share
     */
    fun shareImage(
        context: Context,
        bitmap: Bitmap,
        title: String = "",
        error: String = "",
        packageName: String = ""
    ) {
        try {
            val pathUri = Uri.parse(
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    bitmap,
                    title,
                    null
                )
            )
            shareImage(context, pathUri, error, packageName)
        } catch (ex: Exception) {
            showText(context, if (error.isNotEmpty()) error else getString(R.string.error_share))
        }
    }

    /**
     * Share image from intent
     * @param context: Context
     * @param path: path of image like explore
     * @param error: Message when no intent share
     */
    fun shareImage(context: Context, path: String, error: String = "", packageName: String = "") {
        try {
            val bitmap = BitmapFactory.decodeFile(path)
            shareImage(context, bitmap, error, packageName)
        } catch (e: Exception) {
            showText(context, if (error.isNotEmpty()) error else getString(R.string.error_share))
        }
    }

    /**
     * Share image from intent
     * @param ctx: Context
     * @param uri: URI of image require not null
     * @param error: Message when no intent share
     */
    fun shareImage(context: Context, uri: Uri, error: String = "", packageName: String = "") {
        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "image/*"
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra(Intent.EXTRA_STREAM, uri)
            if (packageName.isNotEmpty()) i.setPackage(packageName)
            context.startActivity(Intent.createChooser(i, "Share use with: "))
        } catch (ex: Exception) {
            showText(context, if (error.isNotEmpty()) error else getString(R.string.error_share))
        }
    }

    /**
     * Sent mail
     * @param context
     * @param subject: title of mail
     * @param message: content of mail
     * @param mailTo: mail to sent
     * @param error: string when no apps open
     */
    fun sentMail(
        context: Context,
        subject: String,
        message: String,
        mailTo: String,
        error: String = ""
    ) {
        try {
            context.startActivity(Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$mailTo")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            })
        } catch (e: Exception) {
            showText(context, if (error.isNotEmpty()) error else getString(R.string.error_sent))
        }
    }
}