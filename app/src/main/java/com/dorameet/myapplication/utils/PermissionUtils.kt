package com.dorameet.myapplication.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * @author 11230
 */
object PermissionUtils {
    fun checkPermission(
        snackBarContainer: View?,
        context: Activity?,
        permissions: ArrayList<String>,
        listener: CheckPermissionListener,
        requestCode: Int,
        snackBarTips: String?
    ): Snackbar? {
        val noPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                noPermissionList.add(permission)
            }
        }
        if (noPermissionList.isEmpty()) {
            listener.onHavePermission()
            return null
        } else {
            val snackbar = Snackbar.make(
                snackBarContainer!!,
                snackBarTips!!, Snackbar.LENGTH_INDEFINITE
            ).setAnchorView(snackBarContainer)
            snackbar.setBackgroundTint(Color.WHITE)
            snackbar.setTextColor(Color.BLACK)
            snackbar.show()
            listener.noHavePermission(snackbar)
            ActivityCompat.requestPermissions(
                context!!,
                noPermissionList.toTypedArray<String>(),
                requestCode
            )
            //在这里设置去请求用户权限
            return snackbar
        }
    }


    interface CheckPermissionListener {
        /**有权限会调用这个 */
        fun onHavePermission()

        /**
         * 默认会有个snackBar提示用户
         * @param snackbar 表示弹出的提示框
         */
        fun noHavePermission(snackbar: Snackbar?)
    }
}