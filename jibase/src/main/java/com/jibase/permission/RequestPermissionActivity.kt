package com.jibase.permission

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.jibase.BaseApp
import com.jibase.R
import com.jibase.permission.dialog.TipDialog
import com.jibase.permission.entities.DialogOption
import com.jibase.permission.listener.OnPermissionResult
import com.jibase.ui.mvp.MVPActivity
import com.jibase.utils.hasResource
import com.jibase.utils.log

@TargetApi(Build.VERSION_CODES.M)
class RequestPermissionActivity : MVPActivity<RequestPermissionContract.View, RequestPermissionContract.Presenter>(), RequestPermissionContract.View {
    companion object {
        private var permissions: Array<String> = arrayOf()
        private var callback: OnPermissionResult? = null
        private var layoutResId: Int = R.layout.permission_default_layout_tip
        private var option: DialogOption? = null
        private var activityTutorial: Class<*>? = null

        fun start(permissions: Array<String>, callback: OnPermissionResult, layoutResId: Int, option: DialogOption?, activityTutorial: Class<*>?) {
            this.permissions = permissions
            this.callback = callback
            if (layoutResId != -1) this.layoutResId = layoutResId
            this.option = option
            this.activityTutorial = activityTutorial
            BaseApp.instance.startActivity(Intent(BaseApp.instance, RequestPermissionActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }


        const val RC_SETTINGS = 6739
        const val RC_PERMISSION = 6937
        const val RC_TIP_PERMISSION = 6677
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        mPresenter.initRequestPermission(this, permissions, hasResource(layoutResId))
    }

    override fun initPresenter() = RequestPermissionPresenterImpl(this)

    override fun onRestart() {
        super.onRestart()
        mPresenter.handleOnRestart()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.handleOnStop()
    }

    override fun onViewListener() {}

    override fun onGrantPermission() {
        callback?.onGranted()
    }

    override fun onDenyPermission(listDeny: List<String>) {
        callback?.onDenied(listDeny)
    }

    override fun onJustBlocked(list: List<String>) {
        callback?.onJustBlocked(list)
    }

    override fun finishActivity() {
        finish()
    }

    override fun showTipDialog(permission: String, message: Pair<String, String>) {
        TipDialog(layoutResId, message, object : TipDialog.CallBack {
            override fun onOkClicked() {
                mPresenter.handleTipOkClicked(this@RequestPermissionActivity)
            }

            override fun onCancelClicked() {
                mPresenter.deny()
            }
        }).show(supportFragmentManager, "")
    }

    override fun showDialogLayout(message: Pair<String, String>) {
        TipDialog(layoutResId, message, object : TipDialog.CallBack {
            override fun onOkClicked() {

            }

            override fun onCancelClicked() {
                mPresenter.deny()
            }
        }).show(supportFragmentManager, "")
    }

    override fun showDialogOption() {
        if (option == null) option = DialogOption()
        val listener = DialogInterface.OnClickListener { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mPresenter.requestListDenyPermission(this)
            } else {
                mPresenter.deny()
            }
        }
        AlertDialog.Builder(this).setTitle(option?.title)
                .setMessage(option?.message)
                .setPositiveButton(option?.textOk, listener)
                .setNegativeButton(option?.textCancel, listener)
                .setOnCancelListener { mPresenter.deny() }.create().show()
    }

    override fun startSettings() {
        // start setting activity
        startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        }, RC_SETTINGS)
    }

    override fun startTutorial() {
        // start the tutorial if available
        activityTutorial?.also {
            startActivity(Intent(this, it).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            })
        }
    }

    override fun goToSetting() {
        if (option == null) option = DialogOption()
        option?.apply {
            if (!isGotoSetting) {
                mPresenter.deny()
                return
            }

            AlertDialog.Builder(this@RequestPermissionActivity).setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(textOk) { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null))
                        startActivityForResult(intent, RC_SETTINGS)
                    }
                    .setNegativeButton(textCancel) { _, _ -> mPresenter.deny() }
                    .setOnCancelListener { mPresenter.deny() }.create().show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        log("OnPermission result----> $requestCode ---> ${permissions.size}    ------> ${grantResults.size}")
        mPresenter.handleRequestPermissionResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.handleActivityResult(requestCode)
    }


    override fun onDestroy() {
        callback = null
        activityTutorial = null
        option = null
        super.onDestroy()
    }
}